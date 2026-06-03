<template>
    <div v-if="isReady" class="flex flex-col items-center gap-4">
        <ResetCountdown class="self-end" />
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayWinnerCount !== undefined">
                <strong>{{ todayWinnerCount }} players </strong> have solved {{ gameModeDisplayName }} #{{ dailyMapNumber }}
            </span>
        </div>
        <div v-if="!gameEnded" class="flex flex-col gap-1 w-full lg:w-3/5 max-w-150">
            <div class="flex flex-col gap-2 lg:gap-4 items-center">
                <h2 class="text-lg lg:text-xl font-semibold">Which map is this?</h2>
                <div class="flex gap-4 w-full">
                    <MapCombobox v-model="selectedMap"
                                 :maps
                                 :picked-maps
                                 name-prop="name"
                                 unique-id="name" />
                    <button class="text-lg lg:text-xl xl:text-2xl rounded-full border-2 border-app-border py-2 px-4 bg-guess-button cursor-pointer hover:scale-105 transition-transform"
                            type="button"
                            :inert="!maps.length || !selectedMap"
                            @click="handleGuess">
                        <span v-if="!isGuessLoading">Guess</span>
                        <Loader v-else />
                    </button>
                </div>
            </div>
            <span v-if="hasMapAlreadyBeenPicked" class="text-sm italic text-red-600 pl-4">You already picked this map.</span>
        </div>
        <ResultScreen v-if="gameEnded"
                      :has-won
                      :answer
                      :game-mode
                      :game-mode-display-name
                      :storage-key>
            <template #shareButton>
                <ShareButton :format-result="formatResult"
                             :game-mode
                             :game-mode-display-name
                             :storage-key />
            </template>
        </ResultScreen>
        <div class="flex flex-wrap gap-2 lg:gap-4">
            <GuessChip v-for="([mapName, guess]) in Object.entries(history)"
                       :key="mapName"
                       :name="mapName"
                       :success="guess.success" />
        </div>
        <div class="flex flex-col w-full gap-2 lg:gap-4 items-center">
            <Picture :src="pictureUrl"
                     :number="1"
                     :blur-level="currentBlur" />
        </div>
        <GiveUpButton v-if="!gameEnded" :game-mode="gameMode" @done="handleGiveUp" />
    </div>
    <div v-else class="flex items-center justify-center h-full">
        <Loader />
    </div>
</template>

<script setup lang="ts">
import GiveUpButton from '#/components/GiveUpButton.vue';
import GuessChip from '#/components/GuessChip.vue';
import Loader from '#/components/Loader.vue';
import MapCombobox from '#/components/MapCombobox.vue';
import Picture from '#/components/Picture.vue';
import ResetCountdown from '#/components/ResetCountdown.vue';
import ResultScreen from '#/components/ResultScreen.vue';
import ShareButton from '#/components/ShareButton.vue';
import { useDailyMapApi } from '#/composables/api/useDailyMapApi';
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { usePictureApi } from '#/composables/api/usePictureApi';
import { useConfetti } from '#/composables/useConfetti';
import { useShare } from '#/composables/useShare';
import { createGameStore } from '#/stores/gameStore';
import type { Answer } from '#/types/api/answer';
import type { BlurMap } from '#/types/api/blurMap';
import type { DailyStats } from '#/types/api/dailyStats';
import type { BlurGameMode } from '#/types/api/gameMode';
import type { Guess } from '#/types/api/guess';
import { storeToRefs } from 'pinia';
import { computed, onMounted, ref, watchEffect } from 'vue';

const { gameMode, storageKey } = defineProps<{
    gameMode: BlurGameMode;
    gameModeDisplayName: string;
    storageKey: string;
}>();

const gameStore = createGameStore(gameMode, storageKey)();
const { isInHistory, historyContainsSuccess } = gameStore;
const { history, dailyMapUuid, dailyMapNumber, playersAverageScore, answer } = storeToRefs(gameStore);
const { triggerConfetti } = useConfetti();
const { hintToEmoji } = useShare();

const isReady = ref(false);

const maps = ref<BlurMap[]>([]);
const todayWinnerCount = ref<number>();

const selectedMap = ref<BlurMap>();
const pickedMaps = computed(() => maps.value.filter((map) => Object.keys(history.value).includes(map.name)));
const hasMapAlreadyBeenPicked = ref(false);

const hasWon = computed(() => historyContainsSuccess());
const hasLost = computed(() => !!answer.value);
const gameEnded = computed(() => hasWon.value || hasLost.value);

const isGuessLoading = ref(false);

/** Animations */
watchEffect(() => {
    if (hasWon.value && isReady.value) {
        triggerConfetti();
    }
});

/** Game core */
const guessApi = useGuessApi();
const dailyStatsApi = useDailyStatsApi();
const mapsApi = useMapsApi();
const dailyMapApi = useDailyMapApi();
const pictureApi = usePictureApi();
const pictureUrl = pictureApi.getBlurPictureUrl(gameMode);

const maxBlur = 7;
const step = 0.7;

const currentBlur = computed(() => {
    if (gameEnded.value) return 0;
    const attemptCount = Object.keys(history.value).length;
    return Math.max(0, maxBlur - attemptCount * step);
});

async function handleGuess() {
    if (!selectedMap.value || !dailyMapUuid.value) return;
    hasMapAlreadyBeenPicked.value = isInHistory(selectedMap.value.name);
    if (hasMapAlreadyBeenPicked.value) return;
    try {
        isGuessLoading.value = true;
        const attemptCount = Object.keys(history.value).length + 1;
        const guess: Guess = await guessApi.postGuess(gameMode, {
            guessedMapName: selectedMap.value.name,
            guessNumber: attemptCount,
            dailyMapUuid: dailyMapUuid.value
        });
        if (guess.isValidDay) {
            history.value[selectedMap.value.name] = guess;
        } else {
            globalThis.location.reload();
        }
    } catch (e) {
        console.error('Error while guessing map', e);
    } finally {
        isGuessLoading.value = false;
    }
}

function formatResult() {
    return `\n${Object.values(history.value).map((guess) => hintToEmoji(guess.success)).join('')}`;
}

function handleGiveUp(_answer: Answer) {
    answer.value = _answer;
}

/** FETCH DATA */

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
        return await dailyMapApi.getDailyMapUuid(gameMode);
    } catch (e) {
        console.error('Error while fetching daily map uuid', e);
    }
    return null;
}

/** FETCH DATA */
async function fetchMaps() {
    try {
        maps.value = await mapsApi.getBlurMaps(gameMode);
        maps.value.sort((a, b) => a.name.localeCompare(b.name));
    } catch (e) {
        console.error('Error while fetching maps', e);
    }
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
    }
    await fetchData();
    isReady.value = true;
});

</script>
