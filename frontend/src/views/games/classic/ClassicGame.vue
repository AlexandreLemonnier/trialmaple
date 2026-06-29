<template>
    <div v-if="isReady" class="flex flex-col items-center gap-4">
        <ResetCountdown class="self-end" />
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayWinnerCount !== undefined">
                <strong>{{ todayWinnerCount }} players </strong> have solved {{ GAME_MODE_DISPLAY_NAMES[gameMode] }} #{{ dailyMapNumber }}
            </span>
        </div>
        <div v-if="!gameEnded" class="flex flex-col gap-1 w-full lg:w-3/5 max-w-150">
            <div class="flex gap-4 w-full">
                <MapCombobox v-model="selectedMap"
                             :maps
                             :picked-maps
                             name-prop="name"
                             unique-id="uuid" />
                <GuessButton :maps
                             :selected-map="selectedMap"
                             :inert="isGuessCardAnimating"
                             :is-loading="isGuessLoading"
                             @click="handleGuess" />
            </div>
            <span v-if="hasMapAlreadyBeenPicked" class="text-sm italic text-red-600 pl-4">You already picked this map.</span>
        </div>
        <ResultScreen v-if="gameEnded"
                      :has-won
                      :answer
                      :game-mode
                      :format-result="formatResult">
        </ResultScreen>
        <div class="flex flex-col w-full gap-5 lg:px-10 xl:px-20">
            <GuessCard v-for="([uuid, guess]) in guessesToIterate"
                       :key="uuid"
                       :map-name="getMapDisplayName(uuid)"
                       :guess
                       :hints-to-display
                       @animationFinished="onGuessCardAnimationFinished"
                       :ignore-animations="ignoreCardsAnimations"
                       :show-login
                       :external-map-url="getExternalMapUrl(uuid)" />
        </div>
        <ExternalMapsListNote :game-mode />
        <GiveUpButton v-if="!gameEnded" :game-mode="gameMode" @done="handleGiveUp" />
    </div>
    <div v-else class="flex items-center justify-center h-full">
        <Loader />
    </div>
</template>

<script setup lang="ts">
import ExternalMapsListNote from '#/components/ExternalMapsListNote.vue';
import GiveUpButton from '#/components/GiveUpButton.vue';
import GuessButton from '#/components/GuessButton.vue';
import GuessCard from '#/components/GuessCard.vue';
import Loader from '#/components/Loader.vue';
import MapCombobox from '#/components/MapCombobox.vue';
import ResetCountdown from '#/components/ResetCountdown.vue';
import ResultScreen from '#/components/ResultScreen.vue';
import { useDailyMapApi } from '#/composables/api/useDailyMapApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { useStatsApi } from '#/composables/api/useStatsApi';
import { useConfetti } from '#/composables/useConfetti';
import { useShare } from '#/composables/useShare';
import { createGameStore } from '#/stores/gameStore';
import { useShareStore } from '#/stores/shareStore';
import type { Answer } from '#/types/api/answer';
import type { DailyStats } from '#/types/api/dailyStats';
import { GAME_MODE_DISPLAY_NAMES, type ClassicGameMode } from '#/types/api/gameMode';
import type { Guess } from '#/types/api/guess';
import type { TmMap } from '#/types/api/tmMap';
import type { HintInformation } from '#/types/HintInformation';
import { getMapList } from '#/utils/getMapList';
import { storeToRefs } from 'pinia';
import { computed, onMounted, ref, watch, watchEffect } from 'vue';

const { gameMode, hintsToDisplay, showLogin = true } = defineProps<{
    gameMode: ClassicGameMode;
    hintsToDisplay: HintInformation[];
    showLogin?: boolean;
}>();

const gameStore = createGameStore(gameMode)();
const { isInHistory, historyContainsSuccess } = gameStore;
const { history, dailyMapUuid, dailyMapNumber, playersAverageScore, answer } = storeToRefs(gameStore);
const { triggerConfetti } = useConfetti();
const { hintToEmoji } = useShare();

const isReady = ref(false);

const maps = ref<TmMap[]>([]);
const todayWinnerCount = ref<number>();

const guessesToIterate = computed(() => {
    const entries = Object.entries(history.value).reverse();
    if (answer.value?.mapUuid && answer.value.answerDetails) {
        // If the player gave up, we want to display the answer at the end of the list of guesses, so we add it to the entries array
        entries.unshift([answer.value.mapUuid, answer.value.answerDetails]);
    }
    return entries;
});

const selectedMap = ref<TmMap>();
const pickedMaps = computed(() => maps.value.filter((map) => Object.keys(history.value).includes(map.uuid)));
const hasMapAlreadyBeenPicked = ref(false);

const hasWon = ref(false);
const hasLost = computed(() => !!answer.value);
const gameEnded = computed(() => hasWon.value || hasLost.value);

const isGuessLoading = ref(false);
const isGuessCardAnimating = ref(false);
const pendingWin = ref(false);
// To avoid animations when initializing history from local storage
const ignoreCardsAnimations = ref(true);

/** Animations */
watchEffect(() => {
    if (hasWon.value && isReady.value) {
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
const dailyMapApi = useDailyMapApi();
const statsApi = useStatsApi();

function getMapDisplayName(uuid: string) {
    const matchingMap = maps.value.find((map) => map.uuid === uuid);
    if (matchingMap) {
        return matchingMap.displayName ?? matchingMap.name;
    }
    return 'Map not found';
}

function getExternalMapUrl(uuid: string) {
    const matchingMap = maps.value.find((map) => map.uuid === uuid);
    const externalMapId = matchingMap?.tmxId ?? null;
    const baseUrl = getMapList(gameMode).url;
    return externalMapId ? `${baseUrl}&map=${externalMapId}` : null;
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

/** Share result */

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

const shareStore = useShareStore();

watchEffect(() => {
    if (gameEnded.value && isReady.value) {
        shareStore.setFormattedShareString(gameMode, formatResult());
    }
});

function handleGiveUp(_answer: Answer) {
    answer.value = _answer;
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
        const dailyStats: DailyStats = await statsApi.getDailyStats(gameMode);
        dailyMapNumber.value = dailyStats.mapNumber;
        todayWinnerCount.value = dailyStats.winnersCount;
        playersAverageScore.value = dailyStats.averageTries;
    } catch (e) {
        console.error('Error while fetching daily stats', e);
    }
}

async function fetchDailyMapUuid() {
    try {
        return await dailyMapApi.getDailyMapUuid(gameMode);
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
    const serverDailyMapUuid = await fetchDailyMapUuid();
    if (serverDailyMapUuid && serverDailyMapUuid !== dailyMapUuid.value) {
        // Delete game data from local storage if daily map has changed
        history.value = {};
        answer.value = null;
        dailyMapUuid.value = serverDailyMapUuid;
    } else if (historyContainsSuccess()) {
        hasWon.value = true;
    }
    await fetchData();
    isReady.value = true;
});

</script>
