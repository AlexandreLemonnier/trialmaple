<template>
    <div v-if="isReady" class="flex flex-col items-center gap-4">
        <ResetCountdown class="self-end" />
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayWinnerCount !== undefined">
                <strong>{{ todayWinnerCount }} players </strong> have solved {{ gameModeDisplayName }} #{{ dailyMapNumber }}
            </span>
        </div>
        <div v-if="!hasWon" class="flex flex-col gap-1 w-full lg:w-3/5 max-w-150">
            <div class="flex gap-4 w-full">
                <MapCombobox v-model="selectedMap"
                             :maps
                             :picked-maps
                             name-prop="name"
                             unique-id="uuid" />
                <button class="text-lg lg:text-xl xl:text-2xl rounded-full border-2 border-app-border py-2 px-4 bg-guess-button cursor-pointer hover:scale-105 transition-transform"
                        type="button"
                        :inert="!maps.length || !selectedMap || isGuessCardAnimating"
                        @click="handleGuess">
                    <span v-if="!isGuessLoading">Guess</span>
                    <Loader v-else />
                </button>
            </div>
            <span v-if="hasMapAlreadyBeenPicked" class="text-sm italic text-red-600 pl-4">You already picked this map.</span>
        </div>
        <WinScreen v-if="hasWon"
                   :game-mode
                   :game-mode-display-name
                   :history-storage-key
                   :daily-map-uuid-storage-key>
            <template #shareButton>
                <ShareButton :format-result="formatResult"
                             :game-mode
                             :game-mode-display-name
                             :history-storage-key
                             :daily-map-uuid-storage-key />
            </template>
        </WinScreen>
        <div class="flex flex-col w-full gap-5 lg:px-10 xl:px-20">
            <GuessCard v-for="([uuid, guess]) in reversedHistory"
                       :key="uuid"
                       :map-name="getMapDisplayName(uuid)"
                       :guess
                       :hints-to-display
                       @animationFinished="onGuessCardAnimationFinished"
                       :ignore-animations="ignoreCardsAnimations"
                       :show-login />
        </div>
        <ExternalMapsListNote :game-mode />
    </div>
    <div v-else class="flex items-center justify-center h-full">
        <Loader />
    </div>
</template>

<script setup lang="ts">
import ExternalMapsListNote from '#/components/ExternalMapsListNote.vue';
import GuessCard from '#/components/GuessCard.vue';
import Loader from '#/components/Loader.vue';
import MapCombobox from '#/components/MapCombobox.vue';
import ResetCountdown from '#/components/ResetCountdown.vue';
import ShareButton from '#/components/ShareButton.vue';
import WinScreen from '#/components/WinScreen.vue';
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { useConfetti } from '#/composables/useConfetti';
import { useShare } from '#/composables/useShare';
import { createGameStore } from '#/stores/gameStore';
import type { DailyStats } from '#/types/api/dailyStats';
import type { ClassicGameMode } from '#/types/api/gameMode';
import type { Guess } from '#/types/api/guess';
import type { TmMap } from '#/types/api/tmMap';
import type { HintInformation } from '#/types/HintInformation';
import { storeToRefs } from 'pinia';
import { computed, onBeforeMount, onMounted, ref, watch } from 'vue';

const { gameMode, historyStorageKey, dailyMapUuidStorageKey, hintsToDisplay, showLogin = true } = defineProps<{
    gameMode: ClassicGameMode;
    gameModeDisplayName: string;
    historyStorageKey: string;
    dailyMapUuidStorageKey: string;
    hintsToDisplay: HintInformation[];
    showLogin?: boolean;
}>();

const gameStore = createGameStore(gameMode, historyStorageKey, dailyMapUuidStorageKey)();
const { isInHistory } = gameStore;
const { history, dailyMapUuid, dailyMapNumber, playersAverageScore } = storeToRefs(gameStore);
const { triggerConfetti } = useConfetti();
const { hintToEmoji } = useShare();

const isReady = ref(false);

const maps = ref<TmMap[]>([]);
const todayWinnerCount = ref<number>();

const reversedHistory = computed(() =>
    Object.entries(history.value).reverse()
);

const selectedMap = ref<TmMap>();
const pickedMaps = computed(() => maps.value.filter((map) => Object.keys(history.value).includes(map.uuid)));
const hasMapAlreadyBeenPicked = ref(false);
const hasWon = ref(false);

const isGuessLoading = ref(false);
const isGuessCardAnimating = ref(false);
const pendingWin = ref(false);
// To avoid animations when initializing history from local storage
const ignoreCardsAnimations = ref(true);

/** Animations */
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

function getMapDisplayName(uuid: string) {
    const matchingMap = maps.value.find((map) => map.uuid === uuid);
    if (matchingMap) {
        return matchingMap.displayName ?? matchingMap.name;
    }
    return 'Map not found';
}

function historyContainsSuccess() {
    return Object.values(history.value).some((guess) => guess.success);
}

async function handleGuess() {
    if (!selectedMap.value || !dailyMapUuid.value) return;
    ignoreCardsAnimations.value = false;
    hasMapAlreadyBeenPicked.value = isInHistory(selectedMap.value.uuid);
    if (hasMapAlreadyBeenPicked.value) return;
    try {
        isGuessLoading.value = true;
        isGuessCardAnimating.value = true;
        const attemptCount = Object.keys(history.value).length + 1;
        const guess: Guess = await guessApi.postGuess(gameMode, {
            guessedMapUuid: selectedMap.value.uuid,
            guessNumber: attemptCount,
            dailyMapUuid: dailyMapUuid.value
        });
        if (guess.isValidDay) {
            history.value[selectedMap.value.uuid] = guess;
        } else {
            globalThis.location.reload();
        }
    } catch (e) {
        console.error('Error while guessing map', e);
        isGuessCardAnimating.value = false;
    } finally {
        isGuessLoading.value = false;
    }
}

function formatGuess(guess: Guess) {
    let result = '';
    const guessProps = hintsToDisplay.map((hint) => hint.guessProp);
    for (const prop of guessProps) {
        const guessHint = guess[prop];
        if (guessHint !== null && typeof guessHint !== 'boolean') {
            if (Array.isArray(guessHint)) {
                result += hintToEmoji(guessHint.some((hintPair) => hintPair.hint));
            } else {
                result += hintToEmoji(guessHint.hint);
            }
        }
    }
    return result;
}

function formatResult() {
    let result = '';
    for (const guess of Object.values(history.value)) {
        result += `\n${formatGuess(guess)}`;
    }
    return result;
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
        maps.value = await mapsApi.getMaps(gameMode);
        maps.value.sort((a, b) => a.name.localeCompare(b.name));
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
    const serverDailyMapUuid = await fetchDailyMapUuid();
    if (serverDailyMapUuid && serverDailyMapUuid !== dailyMapUuid.value) {
        // Delete history from local storage if daily map has changed
        history.value = {};
        dailyMapUuid.value = serverDailyMapUuid;
    } else if (historyContainsSuccess()) {
        hasWon.value = true;
    }
    isReady.value = true;
});

</script>
