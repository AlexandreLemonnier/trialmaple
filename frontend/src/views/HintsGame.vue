<template>
    <div class="flex flex-col items-center gap-4">
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayNbPlayersFound !== undefined && todayAverageTries !== undefined">
                <strong>{{ todayNbPlayersFound }} players </strong> have found today's trial map with an average of <strong>{{ todayAverageTries }} guesses</strong>
            </span>
        </div>
        <div class="flex gap-4 w-full lg:w-2/5">
            <MapSelect :mapNames="mapNames" v-model="selectedMap" />
            <button 
                class="text-lg lg:text-2xl rounded-full border-2 py-2 px-4 bg-guess-button/70 cursor-pointer hover:scale-105 transition-transform" 
                type="button" 
                :inert="!mapNames.length || !selectedMap" 
                @click="guess">Guess
            </button>
        </div>
        <div class="w-full lg:w-4/5">
            <h2 class="text-xl font-semibold">Today's map</h2>
            <div class="border rounded-xl w-full h-10"></div>
        </div>
    </div>
</template>

<script setup lang="ts">
import MapSelect from '#/components/MapSelect.vue';
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { DailyStats } from '#/types/api/dailyStats';
import { Guess } from '#/types/api/guess';
import { onMounted, ref } from 'vue';

const mapNames = ref<string[]>([]);
const todayNbPlayersFound = ref<number>();
const todayAverageTries = ref<number>();

const selectedMap = ref<string>();

const mapsApi = useMapsApi();
const dailyStatsApi = useDailyStatsApi();
const guessApi = useGuessApi();

const knownAuthors = ref<string[]>([]);
const knownDifficulty = ref<string>();
const knownPoints = ref<number>();
const knownCheckpoints = ref<number>();
const knownWR = ref<string | null>();
const knownNbFinishers = ref<number>();

function updateKnownData(guess: Guess) {
    if (guess.difficulty.hint) {
        knownDifficulty.value = guess.difficulty.value;
    }
    if (guess.points.hint === 'EQUAL') {
        knownPoints.value = guess.points.value;
    }
    if (guess.checkpoints.hint === 'EQUAL') {
        knownCheckpoints.value = guess.checkpoints.value;
    }
    if (guess.worldRecord.hint === 'EQUAL') {
        knownWR.value = guess.worldRecord.value;
    }
    if (guess.nbFinishers.hint === 'EQUAL') {
        knownNbFinishers.value = guess.nbFinishers.value;
    }
    knownAuthors.value = [...knownAuthors.value, ...guess.authors.filter((authorHint) => authorHint.hint).map((authorHint) => authorHint.value)];
}

async function guess() {
    if (!selectedMap.value) return;
    try {
        const guess: Guess = await guessApi.postGuess(selectedMap.value);
        updateKnownData(guess);
        // TODO Add guess to history + localStorage + add card
        if (guess.success) {
            // TODO Handle success
        }
    } catch (e) {
        console.error('Error while guessing trial maps', e);
    }
}

/** FETCH DATA */
async function fetchMaps() {
    try {
        mapNames.value = await mapsApi.getMapNames();
        mapNames.value.sort((a, b) => a.localeCompare(b));
    } catch (e) {
        console.error('Error while fetching trial maps', e);
    } 
}

async function fetchDailyStats() {
    try {
        const dailyStats: DailyStats = await dailyStatsApi.getDailyStats();
        todayNbPlayersFound.value = dailyStats.nbWinners;
        todayAverageTries.value = dailyStats.averageTries;
    } catch (e) {
        console.error('Error while fetching daily stats', e);
    }
}

async function fetchData() {
    await fetchMaps();
    await fetchDailyStats();
}

onMounted(async () => {
    fetchData();
})
</script>