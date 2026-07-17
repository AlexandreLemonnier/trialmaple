<template>
    <div v-if="isReady" class="flex flex-col items-center gap-4">
        <ResetCountdown class="self-end" />
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayWinnerCount !== undefined">
                <strong>{{ todayWinnerCount }} players </strong> have solved {{ GAME_MODE_DISPLAY_NAMES[gameMode] }} #{{ dailyMapNumber }}
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
                    <GuessButton :maps
                                 :selected-map="selectedMap"
                                 :is-loading="isGuessLoading"
                                 @click="handleGuess" />
                </div>
            </div>
            <span v-if="hasMapAlreadyBeenPicked" class="text-sm italic text-red-600 pl-4">You already picked this map.</span>
        </div>
        <ResultScreen v-if="gameEnded"
                      :has-won
                      :answer
                      :game-mode
                      :format-result="formatResult">
        </ResultScreen>
        <div v-if="gameEnded" class="flex items-center justify-center gap-2 text-sm text-text-muted bg-subcard-background shadow-sm py-2 px-2 lg:px-4 rounded-lg w-fit mx-auto">
            <span>💡</span>
            <p>Want to help improve the game? Send me map screenshots on Discord!</p>
        </div>
        <div class="flex flex-wrap gap-2 lg:gap-4">
            <GuessChip v-for="([mapName, guess]) in Object.entries(history)"
                       :key="mapName"
                       :name="mapName"
                       :success="guess.success" />
        </div>
        <div v-if="nextPictureAttempt && !hasWon" class="text-sm text-text-muted">
            <span>
                Next picture in
                <strong class="text-text-primary font-semibold">
                    {{ attemptsUntilNextPicture }}
                </strong>
                {{ attemptsUntilNextPicture > 1 ? 'attempts' : 'attempt' }}
            </span>
        </div>
        <div class="flex flex-col w-full gap-2 lg:gap-4 items-center">
            <Picture v-for="n in reversedPicturesRange"
                     :key="n"
                     :src="getPictureUrl(n)"
                     :number="n" />
        </div>
        <GiveUpButton v-if="!gameEnded" :game-mode="gameMode" @done="handleGiveUp" />
    </div>
    <div v-else class="flex items-center justify-center h-full">
        <Loader />
    </div>
</template>

<script setup lang="ts">
import GiveUpButton from '#/components/GiveUpButton.vue';
import GuessButton from '#/components/GuessButton.vue';
import GuessChip from '#/components/GuessChip.vue';
import Loader from '#/components/Loader.vue';
import MapCombobox from '#/components/MapCombobox.vue';
import Picture from '#/components/Picture.vue';
import ResetCountdown from '#/components/ResetCountdown.vue';
import ResultScreen from '#/components/ResultScreen.vue';
import { useDailyMapApi } from '#/composables/api/useDailyMapApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { usePictureApi } from '#/composables/api/usePictureApi';
import { useStatsApi } from '#/composables/api/useStatsApi';
import { useConfetti } from '#/composables/useConfetti';
import { useShare } from '#/composables/useShare';
import { createGameStore } from '#/stores/gameStore';
import { useShareStore } from '#/stores/shareStore';
import type { Answer } from '#/types/api/answer';
import type { DailyStats } from '#/types/api/dailyStats';
import { GAME_MODE_DISPLAY_NAMES, type GeoguessrGameMode } from '#/types/api/gameMode';
import type { GeoguessrMap } from '#/types/api/geoguessrMap';
import type { Guess } from '#/types/api/guess';
import { storeToRefs } from 'pinia';
import { computed, onMounted, ref, watchEffect } from 'vue';

const { gameMode, picturesThresholds = [1, 3, 5] } = defineProps<{
    gameMode: GeoguessrGameMode;
    picturesThresholds?: number[];
}>();

const gameStore = createGameStore(gameMode)();
const { isInHistory, historyContainsSuccess } = gameStore;
const { history, dailyMapUuid, dailyMapNumber, playersAverageScore, answer } = storeToRefs(gameStore);
const { triggerConfetti } = useConfetti();
const { hintToEmoji } = useShare();

const isReady = ref(false);

const maps = ref<GeoguessrMap[]>([]);
const todayWinnerCount = ref<number>();

const selectedMap = ref<GeoguessrMap>();
const pickedMaps = computed(() => maps.value.filter((map) => Object.keys(history.value).includes(map.name)));
const hasMapAlreadyBeenPicked = ref(false);

const hasWon = computed(() => historyContainsSuccess());
const hasLost = computed(() => !!answer.value);
const gameEnded = computed(() => hasWon.value || hasLost.value);

const isGuessLoading = ref(false);

/** Pictures and attemps logic */
// Current attempt number
const currentAttempt = computed(() => {
    return Object.keys(history.value).length + (hasWon.value ? 0 : 1);
});
// Number of unlocked pictures
const picturesCount = computed(() => {
    return picturesThresholds.filter((threshold) => currentAttempt.value >= threshold).length;
});
// Array for the loop (so that the most recent picture is shown on top)
const reversedPicturesRange = computed(() =>
    Array.from({ length: picturesCount.value }, (_, i) => picturesCount.value - i)
);
// Next attempt that will unlock a new picture
const nextPictureAttempt = computed(() => {
    return picturesThresholds.find((threshold) => threshold > currentAttempt.value) || null;
});
// Number of attemps left before unlocking next picture
const attemptsUntilNextPicture = computed(() => {
    if (!nextPictureAttempt.value) return 0;
    return nextPictureAttempt.value - currentAttempt.value;
});

/** Animations */
watchEffect(() => {
    if (hasWon.value && isReady.value) {
        triggerConfetti();
    }
});

/** Game core */
const guessApi = useGuessApi();
const statsApi = useStatsApi();
const mapsApi = useMapsApi();
const dailyMapApi = useDailyMapApi();
const pictureApi = usePictureApi();

function getPictureUrl(pictureNumber: number) {
    return pictureApi.getGeoguessrPictureUrl(gameMode, pictureNumber);
}

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
    return Object.keys(history.value).length ? `\n${Object.values(history.value).map((guess) => hintToEmoji(guess.success)).join('')}` : '';
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

/** FETCH DATA */

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

/** FETCH DATA */
async function fetchMaps() {
    try {
        maps.value = await mapsApi.getGeoguessrMaps(gameMode);
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
