<template>
    <div class="text-center text-lg pt-4">
        <span v-if="todayNbPlayersFound !== undefined && todayAverageTries !== undefined">
            <strong>{{ todayNbPlayersFound }} players </strong> have found today's trial map with an average of <strong>{{ todayAverageTries }} guesses</strong>
        </span>
    </div>
</template>

<script setup lang="ts">
import { useApi } from '#/composables/useApi';
import { DailyStats } from '#/types/api/dailyStats';
import { onMounted, ref } from 'vue';

const todayNbPlayersFound = ref<number>();
const todayAverageTries = ref<number>();
const { getDailyStats } = useApi();

onMounted(async () => {
    try {
        const dailyStats: DailyStats = await getDailyStats();
        todayNbPlayersFound.value = dailyStats.nbWinners;
        todayAverageTries.value = dailyStats.averageTries;
    } catch (e) {
        console.error(e);
    }
})
</script>