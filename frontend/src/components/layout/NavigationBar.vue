<template>
    <header class="sticky flex items-center gap-2 lg:gap-6 text-md lg:text-xl font-semibold px-2 lg:px-5 w-full h-8 lg:h-12 bg-navigation-bar-background border-b border-app-border/20">
        <span class="text-sm uppercase tracking-wide">Game mode</span>
        <div v-for="tab in tabs"
             class="px-2 lg:px-3 py-0.5 lg:py-1.5 rounded-lg font-medium cursor-pointer hover:bg-navigation-bar-selection-background transition"
             :class="isActive(tab.routeName) ? 'underline underline-offset-2 bg-navigation-bar-selection-background text-success' : ''"
             @click="navigateToRoute(tab.routeName)">{{ tab.title }}</div>
    </header>
</template>

<script setup lang="ts">
import { Route } from '#/router/Route';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const tabs: {
    title: string;
    routeName: Route;
}[] = [
    {
        title: 'Trial',
        routeName: Route.TMNF_TRIAL_CLASSIC_MODE
    },
    {
        title: 'RPG',
        routeName: Route.TMNF_RPG_CLASSIC_MODE
    }
];

function isActive(routeName: Route) {
    return route.name === routeName;
}

async function navigateToRoute(routeName: Route) {
    if (routeName === route.name) return;
    await router.push({ name: routeName });
}
</script>
