<template>
    <header class="sticky flex justify-between items-center px-2 lg:px-5 w-full h-10 lg:h-14 bg-header-background tracking-wide">
        <div class="flex items-center gap-2 select-none">
            <TrackManiaLogo aria-label="TrialMaple logo" class="w-auto h-[clamp(2.5rem,4vw,4rem)]" />
            <h1 class="flex font-bold">
                <span class="text-[clamp(1.25rem,2.5vw,1.75rem)]">TM</span>
                <span class="text-[clamp(1rem,1.4vw,1.15rem)] font-semibold self-center">-</span>
                <span class="text-[clamp(1.25rem,2.5vw,1.75rem)]">M</span>
                <span class="text-[clamp(1rem,2vw,1.5rem)] font-semibold">ap</span>
                <span class="text-[clamp(0.75rem,1.4vw,1.15rem)] font-semibold">le</span>
            </h1>
        </div>
        <div class="flex gap-4 lg:gap-6 items-center">
            <ThemeToggle />
            <Icon class="cursor-pointer" name="circle-info" size="md" @click="isInfoModalOpen = true" />
            <Icon class="text-discord cursor-pointer" name="discord" size="md" @click="isDiscordModalOpen = true" />
        </div>
    </header>
    <InfoModal v-model="isInfoModalOpen" />
    <DiscordModal v-model="isDiscordModalOpen" />
</template>

<script setup lang="ts">
import TrackManiaLogo from '#/assets/TrackMania-Logo.svg?component';
import Icon from '#/components/Icon.vue';
import ThemeToggle from '#/components/layout/ThemeToggle.vue';
import DiscordModal from '#/components/modal/DiscordModal.vue';
import InfoModal from '#/components/modal/InfoModal.vue';
import { ref } from 'vue';

const isInfoModalOpen = ref(false);
const isDiscordModalOpen = ref(false);
</script>
