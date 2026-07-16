<template>
    <div class="p-6 md:p-8 bg-app-background min-h-screen">

        <!-- Bouton retour (Pratique pour l'admin) -->
        <div class="mb-6">
            <Button class="p-button-text text-app-text-muted" icon="pi pi-arrow-left" label="Back to users" @click="$router.push('/admin/users')" />
        </div>

        <div v-if="user" class="max-w-7xl mx-auto space-y-8">

            <!-- 1. En-tête : Informations du joueur -->
            <div class="bg-card-background p-6 rounded-3xl border border-app-border/50 shadow-lg flex items-center gap-6">
                <!-- Avatar avec Fallback -->
                <div class="w-24 h-24 rounded-full bg-subcard-background border-2 border-app-border flex items-center justify-center overflow-hidden shrink-0">
                    <img v-if="user.avatar" :src="user.avatar" alt="Avatar" class="w-full h-full object-cover" />
                    <i v-else class="pi pi-user text-4xl text-app-text-muted"></i>
                </div>

                <div class="flex flex-col gap-2">
                    <h1 class="text-3xl font-extrabold flex items-baseline gap-1">
                        {{ user.username }}
                        <span v-if="user.discriminator" class="text-xl text-app-text-muted font-normal">#{{ user.discriminator }}</span>
                    </h1>

                    <div class="flex items-center gap-3">
                        <span class="px-3 py-1 text-xs font-semibold rounded-full"
                              :class="{
                                  'bg-blue-100 text-blue-700': user.userType === 'USER',
                                  'bg-purple-100 text-purple-700': user.userType === 'ADMIN'
                              }">
                            {{ user.userType }}
                        </span>
                        <span class="text-sm text-app-text-muted font-mono bg-subcard-background px-2 py-1 rounded">
                            ID: {{ user.discordId }}
                        </span>
                    </div>
                </div>
            </div>

            <!-- 2. Barre de Filtres (Inspirée de UserStats.vue) -->
            <div class="bg-card-background p-4 md:p-6 rounded-2xl border border-app-border/50 shadow-sm flex flex-col md:flex-row gap-6 items-start md:items-center">
                <div class="flex flex-col gap-2 w-full md:w-auto">
                    <span class="text-sm font-semibold uppercase tracking-wider text-app-text-muted">Games</span>
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
                    <span class="text-sm font-semibold uppercase tracking-wider text-app-text-muted">Categories</span>
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

            <!-- 3. Grille des Statistiques -->
            <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
                <!-- État vide -->
                <div v-if="filteredStats.length === 0" class="col-span-full text-center py-12 bg-card-background rounded-3xl border border-app-border border-dashed">
                    <i class="pi pi-chart-pie text-4xl text-app-text-muted mb-3 block"></i>
                    <p class="text-app-text-muted text-lg">No statistics match these filters.</p>
                </div>

                <!-- Cartes de stats (Version épurée de UserStats.vue) -->
                <div v-for="(stat, index) in filteredStats" :key="index"
                     class="flex flex-col bg-card-background rounded-3xl p-6 border border-app-border hover:border-primary-indigo/50 transition-colors">

                    <div class="items-start mb-6">
                        <h2 class="text-xl font-bold mb-2">{{ GAME_MODE_DISPLAY_NAMES[stat.gameMode] || stat.gameMode }}</h2>
                        <div class="flex gap-2">
                            <span class="text-xs text-app-text-muted font-semibold bg-subcard-background px-2.5 py-1 rounded-md border border-app-border/50">{{ stat.tmGame }}</span>
                            <span class="text-xs text-app-text-muted font-semibold bg-subcard-background px-2.5 py-1 rounded-md border border-app-border/50">{{ stat.tmCategory }}</span>
                        </div>
                    </div>

                    <div class="grow space-y-6 mt-auto">
                        <!-- Essais moyens -->
                        <div class="bg-subcard-background rounded-2xl p-4 flex items-center justify-between border border-app-border/30">
                            <span class="font-medium text-app-text-muted">Average tries</span>
                            <div class="flex items-baseline gap-1">
                                <span class="text-3xl font-black">{{ stat.averageTries.toFixed(1) }}</span>
                                <span class="text-sm text-app-text-muted">/map</span>
                            </div>
                        </div>

                        <!-- Win Rate et ProgressBar -->
                        <div>
                            <div class="flex justify-between items-end mb-2">
                                <span class="font-medium text-app-text-muted">Win Rate</span>
                                <div class="text-right">
                                    <span class="text-2xl font-bold">{{ getWinRate(stat.winsCount, stat.dailyMapsCount) }}%</span>
                                </div>
                            </div>
                            <div class="h-2.5 w-full bg-subcard-background border border-app-border/50 rounded-full overflow-hidden">
                                <div class="h-full rounded-full bg-linear-to-r from-primary-emerald to-primary-indigo transition-all duration-500"
                                     :style="{ width: `${getWinRate(stat.winsCount, stat.dailyMapsCount)}%` }">
                                </div>
                            </div>
                            <div class="text-right mt-1.5">
                                <span class="text-xs text-app-text-muted font-medium">{{ stat.winsCount }} wins out of {{ stat.dailyMapsCount }} maps</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import { GAME_MODE_DISPLAY_NAMES } from '#/types/api/gameMode';
import type { User } from '#/types/api/user';
import type { UserStats } from '#/types/api/userStats';
import { TM_CATEGORIES, type TmCategory } from '#/types/tmCategory';
import { TM_GAMES, type TmGame } from '#/types/tmGame';
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const isLoading = ref(true);

// ------------------------------------------------------------------------
// BDD FICTIVE (MOCK) - À remplacer par ton appel API plus tard
// ------------------------------------------------------------------------
const user = ref<User | null>({
    discordId: '123456789012345678',
    username: 'TrialMaster',
    avatar: 'https://i.pravatar.cc/150?img=11',
    discriminator: '4242',
    userType: 'USER'
});

const stats = ref<UserStats[]>([
    { gameMode: 'CLASSIC_TMNF_TRIAL', tmGame: 'TM2020', tmCategory: 'Trial', averageTries: 3.2, winsCount: 45, dailyMapsCount: 50 },
    { gameMode: 'CLASSIC_TM2_RPG', tmGame: 'TM2020', tmCategory: 'RPG', averageTries: 14.8, winsCount: 5, dailyMapsCount: 25 },
    { gameMode: 'BLUR_TMNF_TRIAL', tmGame: 'TMNF', tmCategory: 'Trial', averageTries: 1.5, winsCount: 98, dailyMapsCount: 100 },
    { gameMode: 'ZOOM_TMNF_TRIAL', tmGame: 'TM2020', tmCategory: 'Trial', averageTries: 8.4, winsCount: 12, dailyMapsCount: 30 }
]);

// ------------------------------------------------------------------------
// LOGIQUE DE FILTRAGE (Adaptée de UserStats.vue)
// ------------------------------------------------------------------------
const selectedGames = ref<Set<TmGame>>(new Set(TM_GAMES));
const selectedCategories = ref<Set<TmCategory>>(new Set(TM_CATEGORIES));

onMounted(async () => {
    // const discordId = route.params.id;
    // try {
    //     user.value = await adminApi.getUser(discordId);
    //     stats.value = await adminApi.getUserStats(discordId);
    // } catch (e) {
    //     console.error(e);
    // } finally {
    //     isLoading.value = false;
    // }
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
