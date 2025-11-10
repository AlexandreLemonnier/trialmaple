<template>
    <div class="flex flex-col items-center gap-4">
        <div class="text-md lg:text-lg pt-4 text-center">
            <span v-if="todayNbPlayersFound !== undefined && todayAverageTries !== undefined">
                <strong>{{ todayNbPlayersFound }} players </strong> have found today's trial map with an average of <strong>{{ todayAverageTries }} guesses</strong>
            </span>
        </div>
        <div class="flex gap-4 w-full lg:w-2/5 text-lg lg:text-2xl">
            <select 
                v-model="selectedMap" 
                class="w-full rounded-full border-2 py-2 px-4 outline-none ring-0"
                :inert="!trialMaps.length"
            >
                <option v-for="trialMap in trialMaps" class="text-sm" :key="trialMap.name" :value="trialMap.name">{{ trialMap.name }}</option>
            </select>
            <button class="rounded-full border-2 py-2 px-4 bg-guess-button/70 cursor-pointer" type="button" :inert="!trialMaps.length" @click="guess">Guess!</button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useDailyStatsApi } from '#/composables/api/useDailyStatsApi';
import { useMapsApi } from '#/composables/api/useMapsApi';
import { DailyStats } from '#/types/api/dailyStats';
import { TrialMap } from '#/types/api/trialMap';
import { onMounted, ref } from 'vue';

const trialMaps = ref<TrialMap[]>([]);
const todayNbPlayersFound = ref<number>();
const todayAverageTries = ref<number>();
const mapsApi = useMapsApi();
const dailyStatsApi = useDailyStatsApi();

const selectedMap = ref('');

async function guess() {
    console.log(selectedMap.value);
}

/** FETCH DATA */
async function fetchMaps() {
    try {
        trialMaps.value = await mapsApi.getMaps();
        trialMaps.value.sort((a, b) => a.name.localeCompare(b.name));
        const firstTrialMap = trialMaps.value[0];
        if (firstTrialMap) {
            selectedMap.value = firstTrialMap.name;
        }
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