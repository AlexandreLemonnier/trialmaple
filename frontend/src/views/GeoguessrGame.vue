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
                             unique-id="name" />
                <button class="text-lg lg:text-xl xl:text-2xl rounded-full border-2 border-app-border py-2 px-4 bg-guess-button cursor-pointer hover:scale-105 transition-transform"
                        type="button"
                        :inert="!maps.length || !selectedMap"
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
        <div class="flex flex-wrap gap-2 lg:gap-4">
            <GuessChip v-for="([mapName, guess]) in Object.entries(history)"
                       :key="mapName"
                       :name="mapName"
                       :success="guess.success" />
        </div>
        <div class="flex flex-col w-full gap-2 lg:gap-4 items-center">
            <Picture v-for="n in reversedPicturesRange"
                     :key="n"
                     :src="getPictureUrl(n)"
                     :number="n" />
        </div>
    </div>
    <div v-else class="flex items-center justify-center h-full">
        <Loader />
    </div>
</template>

<script setup lang="ts">
import GuessChip from '#/components/GuessChip.vue';
import Loader from '#/components/Loader.vue';
import MapCombobox from '#/components/MapCombobox.vue';
import Picture from '#/components/Picture.vue';
import ResetCountdown from '#/components/ResetCountdown.vue';
import ShareButton from '#/components/ShareButton.vue';
import WinScreen from '#/components/WinScreen.vue';
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useGuessApi } from '#/composables/api/useGuessApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { usePictureApi } from '#/composables/api/usePictureApi';
import { useConfetti } from '#/composables/useConfetti';
import { useShare } from '#/composables/useShare';
import { createGameStore } from '#/stores/gameStore';
import type { DailyStats } from '#/types/api/dailyStats';
import type { GeoguessrGameMode } from '#/types/api/gameMode';
import type { GeoguessrMap } from '#/types/api/geoguessrMap';
import type { Guess } from '#/types/api/guess';
import { storeToRefs } from 'pinia';
import { computed, onBeforeMount, onMounted, ref, watch } from 'vue';

const { gameMode, historyStorageKey, dailyMapUuidStorageKey } = defineProps<{
    gameMode: GeoguessrGameMode;
    gameModeDisplayName: string;
    historyStorageKey: string;
    dailyMapUuidStorageKey: string;
}>();

const MAX_PICTURES_NUMBER = 3;

const gameStore = createGameStore(gameMode, historyStorageKey, dailyMapUuidStorageKey)();
const { isInHistory } = gameStore;
const { history, dailyMapUuid, dailyMapNumber, playersAverageScore } = storeToRefs(gameStore);
const { triggerConfetti } = useConfetti();
const { hintToEmoji } = useShare();

const isReady = ref(false);

const picturesCount = computed(() => {
    return Math.min(MAX_PICTURES_NUMBER, Object.keys(history.value).length + 1);
});
const reversedPicturesRange = computed(() =>
    Array.from({ length: picturesCount.value }, (_, i) => picturesCount.value - i)
);

const maps = ref<GeoguessrMap[]>([]);
const todayWinnerCount = ref<number>();

const selectedMap = ref<GeoguessrMap>();
const pickedMaps = computed(() => maps.value.filter((map) => Object.keys(history.value).includes(map.name)));
const hasMapAlreadyBeenPicked = ref(false);
const hasWon = ref(false);

const isGuessLoading = ref(false);

/** Animations */
watch(hasWon, () => {
    if (hasWon.value) {
        triggerConfetti();
    }
});

/** Game core */
const guessApi = useGuessApi();
const dailyStatsApi = useDailyStatsApi();
const mapsApi = useMapsApi();
const pictureApi = usePictureApi();

function getPictureUrl(attempt: number) {
    return pictureApi.getPictureUrl(gameMode, attempt);
}

function historyContainsSuccess() {
    return Object.values(history.value).some((guess) => guess.success);
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
    return `\n${Object.values(history.value).map((guess) => hintToEmoji(guess.success)).join('')}`;
}

/** Local storage */
watch(history, () => {
    if (historyContainsSuccess()) {
        hasWon.value = true;
    }
}, { deep: true });

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
        return await guessApi.getDailyMapUuid(gameMode);
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
