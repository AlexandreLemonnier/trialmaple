<template>
    <div class="p-6 md:p-8 bg-app-background min-h-screen">

        <div v-if="isLoading" class="grow flex flex-col items-center justify-center gap-4">
            <Loader loading-reason="Loading user data..." />
        </div>

        <div v-else-if="user" class="max-w-7xl mx-auto space-y-8">

            <!-- Player information -->
            <Card class="shadow-lg flex items-center gap-6">
                <div class="w-24 h-24 rounded-full border-2 border-app-border flex items-center justify-center overflow-hidden shrink-0">
                    <img :src="getDiscordAvatarUrl(user.discordId, user.avatar, user.discriminator)" alt="Avatar" class="w-full h-full object-cover" />
                </div>

                <div class="flex flex-col gap-2">
                    <H1>{{ user.username }}</H1>
                    <div class="flex items-center gap-3">
                        <RolePill :user-type="user.userType" />
                        <SubCard class="text-sm font-mono px-2 py-1">Discord ID: {{ user.discordId }}</SubCard>
                    </div>
                </div>
            </Card>

            <!-- Stats Filters -->
            <Card class="shadow-sm flex flex-col md:flex-row justify-between gap-6 items-start md:items-center">
                <div class="flex flex-col gap-2 w-full md:w-auto">
                    <span class="text-sm font-semibold uppercase tracking-wider text-text-muted">Games</span>
                    <div class="flex flex-wrap gap-2">
                        <Button v-for="tmGame in TM_GAMES"
                                :key="tmGame"
                                :class="!selectedGames.has(tmGame) && 'bg-transparent'"
                                :label="tmGame"
                                @click="selectedGames = toggleFilter(selectedGames, tmGame)">
                            {{ tmGame }}
                        </Button>
                    </div>
                </div>

                <div class="hidden md:block w-px h-12 border border-app-border"></div>

                <div class="flex flex-col gap-2 w-full md:w-auto">
                    <span class="text-sm font-semibold uppercase tracking-wider text-text-muted">Categories</span>
                    <div class="flex flex-wrap gap-2">
                        <Button v-for="tmCategory in TM_CATEGORIES"
                                :key="tmCategory"
                                :class="!selectedCategories.has(tmCategory) && 'bg-transparent'"
                                :label="tmCategory"
                                @click="selectedCategories = toggleFilter(selectedCategories, tmCategory)">
                            {{ tmCategory }}
                        </Button>
                    </div>
                </div>
            </Card>

            <!-- Stats Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
                <!-- Empty State -->
                <Card v-if="filteredStats.length === 0" class="col-span-full text-center border-dashed">
                    <i class="pi pi-chart-pie text-4xl text-text-muted mb-3 block"></i>
                    <p class="text-text-muted text-lg">No statistics match these filters.</p>
                </Card>

                <!-- Stats Cards -->
                <Card v-for="(stat) in filteredStats" :key="stat.gameMode" class="flex flex-col">

                    <div class="items-start mb-6">
                        <h2 class="text-xl font-bold mb-2">{{ GAME_MODE_DISPLAY_NAMES[stat.gameMode] }}</h2>
                        <div class="flex gap-2">
                            <Tag severity="secondary" :value="stat.tmGame" />
                            <Tag class="text-text-muted" severity="secondary" :value="stat.tmCategory" />
                            <SubCard class="text-xs font-semibold text-text-muted px-2.5 py-1">{{ stat.tmGame }}</SubCard>
                            <SubCard class="text-xs font-semibold text-text-muted px-2.5 py-1">{{ stat.tmCategory }}</SubCard>
                        </div>
                    </div>

                    <div class="grow space-y-6 mt-auto">
                        <!-- Average Tries -->
                        <SubCard class="rounded-2xl">
                            <div class="flex items-center justify-between p-4">
                                <span class="font-medium text-text-muted">Average tries</span>
                                <div class="flex items-baseline gap-1">
                                    <span class="text-3xl font-black">{{ stat.averageTries.toFixed(1) }}</span>
                                    <span class="text-sm text-text-muted">/map</span>
                                </div>
                            </div>
                        </SubCard>

                        <!-- Win Rate & ProgressBar -->
                        <div>
                            <div class="flex justify-between items-end mb-2">
                                <span class="font-medium text-text-muted">Win Rate</span>
                                <div class="text-right">
                                    <span class="text-2xl font-bold">{{ getWinRate(stat.winsCount, stat.dailyMapsCount) }}%</span>
                                </div>
                            </div>
                            <SubCard class="h-2.5 w-full rounded-full overflow-hidden">
                                <div class="h-full rounded-full bg-linear-to-r from-brand-primary/30 to-brand-primary transition-all duration-500"
                                     :style="{ width: `${getWinRate(stat.winsCount, stat.dailyMapsCount)}%` }">
                                </div>
                            </SubCard>
                            <div class="text-right mt-1.5">
                                <span class="text-xs text-text-muted font-medium">{{ stat.winsCount }} wins out of {{ stat.dailyMapsCount }} maps</span>
                            </div>
                        </div>
                    </div>
                </Card>
            </div>

        </div>
        <div v-else class="grow flex items-center justify-center gap-4">
            <p class="text-text-muted text-xl font-medium">User not found.</p>
        </div>
    </div>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import Card from '#/components/Card.vue';
import H1 from '#/components/H1.vue';
import Loader from '#/components/Loader.vue';
import RolePill from '#/components/RolePill.vue';
import SubCard from '#/components/SubCard.vue';
import { useAdminStatsApi } from '#/composables/api/useAdminStatsApi';
import { useAdminUserApi } from '#/composables/api/useAdminUserApi';
import { useAppStore } from '#/stores/appStore';
import { GAME_MODE_DISPLAY_NAMES } from '#/types/api/gameMode';
import type { User } from '#/types/api/user';
import type { UserStats } from '#/types/api/userStats';
import { TM_CATEGORIES, type TmCategory } from '#/types/tmCategory';
import { TM_GAMES, type TmGame } from '#/types/tmGame';
import { getDiscordAvatarUrl } from '#/utils/getDiscordAvatarUrl';
import Tag from 'primevue/tag';
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const { currentSelectedUser } = useAppStore();
const route = useRoute();
const isLoading = ref(true);

const user = ref<User | null>(null);
const stats = ref<UserStats[]>([]);

const selectedGames = ref<Set<TmGame>>(new Set(TM_GAMES));
const selectedCategories = ref<Set<TmCategory>>(new Set(TM_CATEGORIES));

const adminUserApi = useAdminUserApi();
async function loadUser() {
    const userId = route.params.userId as string;
    // User data is given by the previous page (UsersList.vue) via store
    if (currentSelectedUser?.discordId === userId) {
        user.value = currentSelectedUser;
    } else {
        // Fallback if user data is missing (refresh, direct link, etc)
        try {
            user.value = await adminUserApi.getUserById(userId);
        } catch (e) {
            console.error('Error while fetching user', e);
        }
    }
}

const adminStatsApi = useAdminStatsApi();
async function loadStats() {
    if (!user.value) return;
    try {
        const result = await adminStatsApi.getUserStats(user.value.discordId);
        stats.value = result.filter((stat) => stat.winsCount > 0);
    } catch (e) {
        console.error('Error while fetching user stats', e);
    }
}

onMounted(async () => {
    isLoading.value = true;
    await loadUser();
    await loadStats();
    isLoading.value = false;
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
