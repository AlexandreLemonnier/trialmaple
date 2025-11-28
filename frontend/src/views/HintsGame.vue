<template>
    <div class="flex flex-col items-center gap-4">
        <ResetCountdown class="self-end" />
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayNbPlayersFound !== undefined && todayAverageTries !== undefined">
                <strong>{{ todayNbPlayersFound }} players </strong> have found today's trial map with an average of <strong>{{ todayAverageTries }} guesses</strong>
            </span>
        </div>
        <div class="flex flex-col gap-1 w-full lg:w-2/5">
            <div class="flex gap-4 w-full">
                <MapSelect :map-names="mapNames" v-model="selectedMap" />
                <button class="text-lg lg:text-2xl rounded-full border-2 py-2 px-4 bg-guess-button/70 cursor-pointer hover:scale-105 transition-transform"
                        type="button"
                        :inert="!mapNames.length || !selectedMap || isGuessCardAnimating || hasWon"
                        @click="handleGuess">Guess
                </button>
            </div>
            <span v-if="mapAlreadyPicked" class="text-sm italic text-red-600 pl-4">You already picked this map.</span>
        </div>
        <WinScreen v-if="hasWon" />
        <div class="flex flex-col w-full gap-5 px-20">
            <GuessCard v-for="([mapName, guess]) in reversedHistory"
                       :key="mapName"
                       :map-name
                       :guess
                       @animationFinished="onGuessCardAnimationFinished"
                       :ignore-animations="ignoreCardsAnimations" />
        </div>
    </div>
</template>

<script setup lang="ts">
import GuessCard from '#/components/GuessCard.vue';
import MapSelect from '#/components/MapSelect.vue';
import ResetCountdown from '#/components/ResetCountdown.vue';
import WinScreen from '#/components/WinScreen.vue';
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import type { DailyStats } from '#/types/api/dailyStats';
import type { Guess } from '#/types/api/guess';
import { useStorage } from '@vueuse/core';
import confetti from 'canvas-confetti';
import { computed, onMounted, ref, watch } from 'vue';

/* API data */
const mapNames = ref<string[]>([]);
const todayNbPlayersFound = ref<number>();
const todayAverageTries = ref<number>();
const dailyMapUuid = useStorage<string>('dailyMapUuid', '');

/* Game info */
const selectedMap = ref<string>();
const mapAlreadyPicked = ref(false);
const history = useStorage<Record<string, Guess>>('history', {});
const reversedHistory = computed(() =>
    Object.entries(history.value).reverse()
);
const hasWon = ref(false);

/* Other */
const isGuessCardAnimating = ref(false);
const pendingWin = ref(false);
// To avoid animations when initializing history from local storage
const ignoreCardsAnimations = ref(true);

/** Animations */
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

function onGuessCardAnimationFinished() {
    if (pendingWin.value) {
        hasWon.value = true;
        pendingWin.value = false;
    }
    isGuessCardAnimating.value = false;
}

/** Game core */
const mapsApi = useMapsApi();
const dailyStatsApi = useDailyStatsApi();
const guessApi = useGuessApi();

function historyContainsMap(mapName: string) {
    return Object.keys(history.value).some((mapFromHistory) => mapName === mapFromHistory);
}

async function handleGuess() {
    if (!selectedMap.value || !dailyMapUuid.value) return;
    ignoreCardsAnimations.value = false;
    mapAlreadyPicked.value = historyContainsMap(selectedMap.value);
    if (mapAlreadyPicked.value) return;
    try {
        isGuessCardAnimating.value = true;
        const nbTries = Object.keys(history.value).length + 1;
        const guess: Guess = await guessApi.postGuess(selectedMap.value, nbTries, dailyMapUuid.value);
        if (guess.isValidDay) {
            history.value[selectedMap.value!] = guess;
        } else {
            window.location.reload();
        }
    } catch (e) {
        console.error('Error while guessing trial map', e);
        isGuessCardAnimating.value = false;
    }
}

/** Local storage */
watch(history, () => {
    if (Object.values(history.value).some((guess) => guess.success)) {
        pendingWin.value = true;
    }
}, { immediate: true, deep: true });

/** FETCH DATA */
async function fetchMaps() {
    try {
        mapNames.value = await mapsApi.getMapNames(true);
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

async function fetchDailyMapUuid() {
    try {
        return await guessApi.getDailyMapUuid();
    } catch (e) {
        console.error('Error while fetching daily map uuid', e);
    }
    return null;
}

async function fetchData() {
    await Promise.all([
        fetchMaps(),
        fetchDailyStats()
    ]);
}

onMounted(async () => {
    await fetchData();
    const serverDailyMapUuid = await fetchDailyMapUuid();
    if (serverDailyMapUuid !== dailyMapUuid.value) {
        // Delete local storage history if daily map has changed
        history.value = null;
        dailyMapUuid.value = serverDailyMapUuid;
    }
});
</script>
