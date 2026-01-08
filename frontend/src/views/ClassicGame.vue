<template>
    <div class="flex flex-col items-center gap-4">
        <ResetCountdown class="self-end" />
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayWinnerCount !== undefined">
                <strong>{{ todayWinnerCount }} players </strong> have solved TrialMaple #{{ dailyMapNumber }}
            </span>
        </div>
        <div v-if="!hasWon" class="flex flex-col gap-1 w-full lg:w-3/5 max-w-150">
            <div class="flex gap-4 w-full">
                <MapCombobox :map-names="mapNames" :picked-maps v-model="selectedMap" />
                <button class="text-lg lg:text-xl xl:text-2xl rounded-full border-2 border-app-border py-2 px-4 bg-guess-button cursor-pointer hover:scale-105 transition-transform"
                        type="button"
                        :inert="!mapNames.length || !selectedMap || isGuessCardAnimating"
                        @click="handleGuess">
                    <span v-if="!isGuessLoading">Guess</span>
                    <Loader v-else />
                </button>
            </div>
            <span v-if="hasMapAlreadyBeenPicked" class="text-sm italic text-red-600 pl-4">You already picked this map.</span>
        </div>
        <WinScreen v-if="hasWon"
                   :game-mode
                   :history-storage-key
                   :daily-map-uuid-storage-key>
            <template #shareButton>
                <ShareButton :game-mode
                             :guess-props="hintsToDisplay.map((hint) => hint.guessProp)"
                             :history-storage-key
                             :daily-map-uuid-storage-key />
            </template>
        </WinScreen>
        <div class="flex flex-col w-full gap-5 lg:px-10 xl:px-20">
            <GuessCard v-for="([mapName, guess]) in reversedHistory"
                       :key="mapName"
                       :map-name
                       :guess
                       :hints-to-display
                       @animationFinished="onGuessCardAnimationFinished"
                       :ignore-animations="ignoreCardsAnimations" />
        </div>
    </div>
</template>

<script setup lang="ts">
import GuessCard from '#/components/GuessCard.vue';
import Loader from '#/components/Loader.vue';
import MapCombobox from '#/components/MapCombobox.vue';
import ResetCountdown from '#/components/ResetCountdown.vue';
import ShareButton from '#/components/ShareButton.vue';
import WinScreen from '#/components/WinScreen.vue';
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { createGameStore } from '#/stores/appStore';
import type { DailyStats } from '#/types/api/dailyStats';
import type { GameMode } from '#/types/api/gameMode';
import type { Guess } from '#/types/api/guess';
import confetti from 'canvas-confetti';
import { storeToRefs } from 'pinia';
import { computed, onBeforeMount, onMounted, ref, watch } from 'vue';

const { gameMode, historyStorageKey, dailyMapUuidStorageKey } = defineProps<{
    gameMode: GameMode;
    historyStorageKey: string;
    dailyMapUuidStorageKey: string;
    hintsToDisplay: { label: string, guessProp: keyof Guess }[];
}>();

const gameStore = createGameStore(gameMode, historyStorageKey, dailyMapUuidStorageKey)();
const { isMapInHistory } = gameStore;
const { history, dailyMapUuid, dailyMapNumber, playersAverageScore } = storeToRefs(gameStore);

const mapNames = ref<string[]>([]);
const todayWinnerCount = ref<number>();

const reversedHistory = computed(() =>
    Object.entries(history.value).reverse()
);

const selectedMap = ref<string>();
const pickedMaps = computed(() => mapNames.value.filter((mapName) => Object.keys(history.value).includes(mapName)));
const hasMapAlreadyBeenPicked = ref(false);
const hasWon = ref(false);

const isGuessLoading = ref(false);
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
const guessApi = useGuessApi();
const mapsApi = useMapsApi();
const dailyStatsApi = useDailyStatsApi();

function historyContainsSuccess() {
    return Object.values(history.value).some((guess) => guess.success);
}

async function handleGuess() {
    if (!selectedMap.value || !dailyMapUuid.value) return;
    ignoreCardsAnimations.value = false;
    hasMapAlreadyBeenPicked.value = isMapInHistory(selectedMap.value);
    if (hasMapAlreadyBeenPicked.value) return;
    try {
        isGuessLoading.value = true;
        isGuessCardAnimating.value = true;
        const attemptCount = Object.keys(history.value).length + 1;
        const guess: Guess = await guessApi.postGuess(gameMode, selectedMap.value, attemptCount, dailyMapUuid.value);
        if (guess.isValidDay) {
            history.value[selectedMap.value!] = guess;
        } else {
            window.location.reload();
        }
    } catch (e) {
        console.error('Error while guessing map', e);
        isGuessCardAnimating.value = false;
    } finally {
        isGuessLoading.value = false;
    }
}

/** Local storage */
watch(history, () => {
    if (historyContainsSuccess()) {
        pendingWin.value = true;
    }
}, { deep: true });

/** FETCH DATA */
async function fetchMaps() {
    try {
        mapNames.value = await mapsApi.getMapNames(gameMode);
        mapNames.value.sort((a, b) => a.localeCompare(b));
    } catch (e) {
        console.error('Error while fetching maps', e);
    }
}

async function fetchDailyStats() {
    try {
        const dailyStats: DailyStats = await dailyStatsApi.getDailyStats(gameMode);
        dailyMapNumber.value = dailyStats.mapNumber;
        todayWinnerCount.value = dailyStats.winnersCount;
        playersAverageScore.value = dailyStats.averageTries;
    } catch (e) {
        console.error('Error while fetching daily stats', e);
    }
}

async function fetchDailyMapUuid() {
    try {
        return await guessApi.getDailyMapUuid(gameMode);
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
});

onBeforeMount(async () => {
    // Name of the old history, to be removed later
    localStorage.removeItem('history');
    localStorage.removeItem('dailyMapUuid');

    const serverDailyMapUuid = await fetchDailyMapUuid();
    if (serverDailyMapUuid && serverDailyMapUuid !== dailyMapUuid.value) {
        // Delete history from local storage if daily map has changed
        history.value = {};
        dailyMapUuid.value = serverDailyMapUuid;
    } else if (historyContainsSuccess()) {
        hasWon.value = true;
    }
});

</script>
