<template>
    <div class="flex flex-col items-center gap-4">
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayNbPlayersFound !== undefined && todayAverageTries !== undefined">
                <strong>{{ todayNbPlayersFound }} players </strong> have found today's trial map with an average of <strong>{{ todayAverageTries }} guesses</strong>
            </span>
        </div>
        <div class="flex flex-col gap-1 w-full lg:w-2/5">
            <div class="flex gap-4 w-full">
                <MapSelect :mapNames="mapNames" v-model="selectedMap" />
                <button 
                    class="text-lg lg:text-2xl rounded-full border-2 py-2 px-4 bg-guess-button/70 cursor-pointer hover:scale-105 transition-transform" 
                    type="button" 
                    :inert="!mapNames.length || !selectedMap" 
                    @click="guess">Guess
                </button>
            </div>
            <span v-if="mapAlreadyPicked" class="text-sm italic text-red-600 pl-4">You already picked this map.</span>
        </div>
        <span v-if="hasWon" class="text-5xl lg:text-7xl">Congrats!!!</span>
        <div class="w-full lg:w-4/5">
            <h2 class="text-xl font-semibold">Today's map</h2>
            <div class="border rounded-xl w-full h-10"></div>
        </div>
        <div class="flex flex-col gap-5 mx-20">
            <GuessCard v-for="([mapName, guess]) in reversedHistory" :key="mapName" :mapName :guess />
        </div>
    </div>
</template>

<script setup lang="ts">
import GuessCard from '#/components/GuessCard.vue';
import MapSelect from '#/components/MapSelect.vue';
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { DailyStats } from '#/types/api/dailyStats';
import { Guess } from '#/types/api/guess';
import confetti from "canvas-confetti";
import { computed, onMounted, ref, watch } from 'vue';

const mapNames = ref<string[]>([]);
const todayNbPlayersFound = ref<number>();
const todayAverageTries = ref<number>();

const selectedMap = ref<string>();
const mapAlreadyPicked = ref(false);
const history = ref<Record<string, Guess>>({});
const reversedHistory = computed(() =>
    Object.entries(history.value).reverse()
);
const hasWon = ref(false);

const knownAuthors = ref<string[]>([]);
const knownDifficulty = ref<string>();
const knownPoints = ref<number>();
const knownCheckpoints = ref<number>();
const knownWR = ref<string | null>();
const knownNbFinishers = ref<number>();

function triggerConfetti() {
    const duration = 2000;
    const end = Date.now() + duration;
    (function frame() {
        confetti({
            particleCount: 10,
            spread: 70,
            startVelocity: 30,
            ticks: 60,
            origin: {
                x: Math.random(),
                y: Math.random() - 0.2
            }
        });

        if (Date.now() < end) {
            requestAnimationFrame(frame);
        }
    })();
}

watch(hasWon, () => {
    if (hasWon.value) {
        triggerConfetti();
    }
});

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

const mapsApi = useMapsApi();
const dailyStatsApi = useDailyStatsApi();
const guessApi = useGuessApi();

function historyContainsMap(mapName: string) {
    return Object.keys(history.value).some((mapFromHistory) => mapName === mapFromHistory);
}

async function guess() {
    if (!selectedMap.value || hasWon.value) return;
    mapAlreadyPicked.value = historyContainsMap(selectedMap.value);
    if (mapAlreadyPicked.value) return;
    try {
        const guess: Guess = await guessApi.postGuess(selectedMap.value);
        history.value[selectedMap.value!] = guess;
        updateKnownData(guess);
        // TODO Add guess to localStorage
        if (guess.success) {
            hasWon.value = true;
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