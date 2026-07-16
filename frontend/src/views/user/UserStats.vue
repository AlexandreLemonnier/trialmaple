<template>
    <div class="h-full bg-app-background p-4 md:p-8">
        <h1 class="max-w-7xl mx-auto mb-10 text-center md:text-left text-4xl md:text-5xl font-extrabold bg-linear-to-r from-primary-indigo to-primary-emerald bg-clip-text text-transparent inline-block">
            My statistics 🏆
        </h1>
        <div v-if="isLoading" class="flex items-center justify-center h-full">
            <Loader />
        </div>
        <div v-else class="max-w-7xl mx-auto space-y-8">
            <div class="bg-card-background p-4 md:p-6 rounded-2xl border border-app-border/50 shadow-lg flex flex-col md:flex-row gap-6 items-start md:items-center justify-between">
                <div class="flex flex-col gap-2 w-full md:w-auto">
                    <span class="text-sm font-semibold uppercase tracking-wider">Games</span>
                    <div class="flex flex-wrap gap-2">
                        <Button v-for="tmGame in TM_GAMES"
                                :key="tmGame"
                                class="font-bold border-2"
                                :class="selectedGames.has(tmGame)
                                    ? 'bg-primary-emerald/20 border-primary-emerald text-primary-emerald'
                                    : 'bg-transparent text-app-text-muted'"
                                pill
                                scale
                                :label="tmGame"
                                @click="selectedGames = toggleFilter(selectedGames, tmGame)">
                            {{ tmGame }}
                        </Button>
                    </div>
                </div>
                <div class="hidden md:block w-px h-12 border border-app-border/50"></div>
                <div class="flex flex-col gap-2 w-full md:w-auto">
                    <span class="text-sm font-semibold uppercase tracking-wider">Categories</span>
                    <div class="flex flex-wrap gap-2">
                        <Button v-for="tmCategory in TM_CATEGORIES"
                                :key="tmCategory"
                                class="font-bold border-2"
                                :class="selectedCategories.has(tmCategory)
                                    ? 'bg-primary-indigo/20 border-primary-indigo text-primary-indigo'
                                    : 'bg-transparent text-app-text-muted'"
                                pill
                                scale
                                :label="tmCategory"
                                @click="selectedCategories = toggleFilter(selectedCategories, tmCategory)">
                            {{ tmCategory }}
                        </Button>
                    </div>
                </div>
            </div>
            <TransitionGroup name="list" tag="div" class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6 relative">
                <div v-if="filteredStats.length === 0" class="col-span-full text-center py-12">
                    <p class="text-app-text-muted text-xl">No statistics match these filters.</p>
                </div>
                <div v-for="stat in filteredStats" :key="stat.gameMode"
                     class="flex flex-col bg-card-background rounded-3xl p-6 border border-app-border hover:border-primary-indigo hover:shadow-2xl transition-transform duration-300 transform hover:-translate-y-1 group">
                    <div class="items-start mb-6">
                        <h2 class="text-xl font-bold mb-1 group-hover:text-primary-indigo/80 transition-colors">
                            {{ GAME_MODE_DISPLAY_NAMES[stat.gameMode] }}
                        </h2>
                        <div class="flex gap-2">
                            <span class="text-xs text-app-text-muted font-semibold bg-subcard-background px-2 py-0.5 rounded-md">{{ stat.tmGame }}</span>
                            <span class="text-xs text-app-text-muted font-semibold bg-subcard-background px-2 py-0.5 rounded-md">{{ stat.tmCategory }}</span>
                        </div>
                    </div>
                    <div class="grow space-y-6">
                        <div class="bg-subcard-background rounded-2xl p-4 flex items-center justify-between">
                            <span class="font-medium text-app-text-muted">Average tries</span>
                            <div class="flex items-baseline gap-1">
                                <span class="text-3xl font-black">{{ stat.averageTries.toFixed(1) }}</span>
                                <span class="text-sm text-app-text-muted">/map</span>
                            </div>
                        </div>
                        <div>
                            <div class="flex justify-between items-end mb-2">
                                <span class="font-medium text-app-text-muted">Win Rate</span>
                                <div class="text-right">
                                    <span class="text-2xl font-bold">{{ getWinRate(stat.winsCount, stat.dailyMapsCount) }}%</span>
                                </div>
                            </div>
                            <div class="h-3 w-full bg-subcard-background rounded-full overflow-hidden">
                                <div class="h-full rounded-full bg-linear-to-r from-primary-emerald to-primary-indigo"
                                     :style="{ width: `${getWinRate(stat.winsCount, stat.dailyMapsCount)}%` }">
                                </div>
                            </div>
                            <div class="text-right mt-1.5">
                                <span class="text-xs text-app-text-muted font-medium">{{ stat.winsCount }} wins out of {{ stat.dailyMapsCount }} maps</span>
                            </div>
                        </div>
                    </div>
                </div>
            </TransitionGroup>
        </div>
    </div>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import Loader from '#/components/Loader.vue';
import { useStatsApi } from '#/composables/api/useStatsApi';
import { GAME_MODE_DISPLAY_NAMES } from '#/types/api/gameMode';
import type { UserStats } from '#/types/api/userStats';
import { TM_CATEGORIES, type TmCategory } from '#/types/tmCategory';
import { TM_GAMES, type TmGame } from '#/types/tmGame';
import { useStorage } from '@vueuse/core';
import { computed, onMounted, ref } from 'vue';

const stats = ref<UserStats[]>([]);
const isLoading = ref(true);

const selectedGames = useStorage<Set<TmGame>>('statsFiltersSelectedGames', new Set(TM_GAMES));
const selectedCategories = useStorage<Set<TmCategory>>('statsFiltersSelectedCategories', new Set(TM_CATEGORIES));

const statsApi = useStatsApi();

onMounted(async () => {
    try {
        stats.value = await statsApi.getUserStats();
    } catch (error) {
        console.error('Error while fetching user stats', error);
    } finally {
        isLoading.value = false;
    }
});

const filteredStats = computed(() => {
    return stats.value.filter((stat) =>
        selectedGames.value.has(stat.tmGame) &&
        selectedCategories.value.has(stat.tmCategory)
    );
});

const toggleFilter = <T>(set: Set<T>, value: T): Set<T> => {
    const newSet = new Set(set);

    if (newSet.has(value)) {
        newSet.delete(value);
    } else {
        newSet.add(value);
    }

    return newSet;
};

const getWinRate = (wins: number, total: number) => {
    if (total === 0) return 0;
    return Math.round(wins / total * 100);
};

</script>
