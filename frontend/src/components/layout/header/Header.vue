<template>
    <header class="sticky top-0 z-40 flex justify-between items-center px-2 lg:px-5 w-full h-10 lg:h-14 bg-header-background border-b border-app-border/20 tracking-wide">
        <div class="flex items-center gap-2 select-none cursor-pointer" @click="router.push({ name: Route.HOME })">
            <TrackManiaLogo aria-label="TrialMaple logo" class="w-auto h-[clamp(2.5rem,4vw,4rem)]" />
            <h1 class="flex font-bold">
                <span class="text-[clamp(1.25rem,2.5vw,1.75rem)]">TM</span>
                <span class="text-[clamp(1rem,1.4vw,1.15rem)] font-semibold self-center">-</span>
                <span class="text-[clamp(1.25rem,2.5vw,1.75rem)]">M</span>
                <span class="text-[clamp(1rem,2vw,1.5rem)] font-semibold">ap</span>
                <span class="text-[clamp(0.75rem,1.4vw,1.15rem)] font-semibold">le</span>
            </h1>
        </div>
        <div class="flex gap-3 lg:gap-6 items-center">
            <ThemeToggle />
            <Icon class="cursor-pointer" name="circle-info" size="md" @click="isInfoModalOpen = true" />
            <Avatar v-if="isConnected" />
            <LoginButton v-else />
        </div>
    </header>
    <InfoModal v-model="isInfoModalOpen" />
</template>

<script setup lang="ts">
import TrackManiaLogo from '#/assets/TrackMania-Logo.svg?component';
import Icon from '#/components/Icon.vue';
import Avatar from '#/components/layout/header/Avatar.vue';
import LoginButton from '#/components/layout/header/LoginButton.vue';
import ThemeToggle from '#/components/layout/header/ThemeToggle.vue';
import InfoModal from '#/components/modal/InfoModal.vue';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore.js';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const { isConnected } = storeToRefs(useAppStore());

const isInfoModalOpen = ref(false);
</script>
