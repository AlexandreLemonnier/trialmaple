<template>
    <header ref="navRef"
            class="sticky flex items-center gap-2 lg:gap-6 text-md lg:text-xl font-semibold px-2 lg:px-5 w-full h-8 lg:h-12 bg-navigation-bar-background border-b border-app-border/20">
        <span class="text-sm uppercase tracking-wide shrink-0">Game mode</span>
        <div v-for="tab in tabs"
             :key="tab.title"
             class="relative group">
            <!-- TM game tab -->
            <div class="flex items-center gap-1.5 lg:gap-2 px-2 lg:px-3 py-0.5 lg:py-1.5 rounded-lg font-medium cursor-pointer hover:bg-navigation-bar-selection-background transition"
                 :class="isActive(tab) ? 'underline underline-offset-2 bg-navigation-bar-selection-background text-success' : ''"
                 @click="toggleTab(tab.title)">
                <img :src="tab.icon" alt="" class="w-4 h-4 lg:w-5 lg:h-5 object-contain" />
                <span class="hidden xs:block">{{ tab.title }}</span>
            </div>
            <!-- Sub tab (game mode) -->
            <div class="absolute left-0 top-full w-full group-hover:block bg-navigation-bar-background border border-app-border/20 rounded-lg shadow-lg z-50 min-w-max"
                 :class="openTab === tab.title ? 'block' : 'hidden'">
                <div v-if="!tab.subTabs.length" class="px-2 lg:px-4 py-1.5 lg:py-2 text-sm italic text-center whitespace-nowrap">Coming soon</div>
                <div v-for="subTab in tab.subTabs"
                     :key="subTab.title"
                     class="px-2 lg:px-4 py-1.5 lg:py-2 cursor-pointer text-center whitespace-nowrap hover:bg-navigation-bar-selection-background transition"
                     :class="route.name === subTab.routeName ? 'text-success font-semibold' : ''"
                     @click="navigateToRoute(subTab.routeName)">
                    {{ subTab.title }}
                </div>
            </div>
        </div>
    </header>
</template>

<script setup lang="ts">
import tm2Icon from '#/assets/tm2.png';
import tm2020Icon from '#/assets/tm2020.jpg';
import tmnfIcon from '#/assets/tmnf.jpg';
import { Route } from '#/router/Route';
import { onMounted, onUnmounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const openTab = ref<string | null>(null);
const navRef = ref<HTMLElement | null>(null);

type Tab = {
    title: string;
    icon: string;
    subTabs: {
        title: string;
        routeName: Route;
    }[]
};

const tabs: Tab[] = [
    {
        title: 'TMNF',
        icon: tmnfIcon,
        subTabs: [
            {
                title: 'Trial',
                routeName: Route.TMNF_TRIAL_CLASSIC_MODE
            },
            {
                title: 'RPG',
                routeName: Route.TMNF_RPG_CLASSIC_MODE
            }
        ]
    },
    {
        title: 'TM2',
        icon: tm2Icon,
        subTabs: [
            // {
            //     title: 'Trial',
            //     routeName: Route.TM2_TRIAL_CLASSIC_MODE
            // }
        ]
    },
    {
        title: 'TM2020',
        icon: tm2020Icon,
        subTabs: [
        ]
    }
];

function toggleTab(title: string) {
    openTab.value = openTab.value === title ? null : title;
}

function isActive(tab: Tab) {
    return tab.subTabs.some((sub) => sub.routeName === route.name);
}

async function navigateToRoute(routeName: Route) {
    if (routeName === route.name) return;
    await router.push({ name: routeName });
    openTab.value = null;
}

/* Close on outside click */
function handleClickOutside(event: MouseEvent) {
    if (openTab.value && !navRef.value?.contains(event.target as Node)) {
        openTab.value = null;
    }
}

onMounted(() => {
    document.addEventListener('mousedown', handleClickOutside);
});

onUnmounted(() => {
    document.removeEventListener('mousedown', handleClickOutside);
});
</script>
