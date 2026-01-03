<template>
    <header class="sticky flex justify-between items-center px-2 lg:px-5 w-full h-12 lg:h-16 bg-header-background tracking-wide">
        <div class="flex items-center gap-2 select-none">
            <TrackManiaLogo aria-label="TrialMaple logo" class="w-auto h-[clamp(3rem,4vw,4rem)]" />
            <div class="flex">
                <h1 class="text-[clamp(1.5rem,2.5vw,1.75rem)] font-bold">T</h1>
                <h1 class="text-[clamp(1.25rem,2vw,1.5rem)] font-semibold">rial</h1>
                <h1 class="text-[clamp(1.5rem,2.5vw,1.75rem)] font-bold">M</h1>
                <h1 class="text-[clamp(1.25rem,2vw,1.5rem)] font-semibold">ap</h1>
                <h1 class="text-[clamp(1rem,1.4vw,1.15rem)] font-semibold">le</h1>
            </div>
        </div>
        <div class="flex gap-4 lg:gap-6 items-center">
            <ThemeToggle />
            <Icon class="cursor-pointer" name="circle-info" size="md" @click="isInfoModalOpen = true" />
        </div>
    </header>
    <InfoModal v-model="isInfoModalOpen" />
</template>

<script setup lang="ts">
import TrackManiaLogo from '#/assets/TrackMania-Logo.svg?component';
import Icon from '#/components/Icon.vue';
import ThemeToggle from '#/components/layout/ThemeToggle.vue';
import InfoModal from '#/components/modal/InfoModal.vue';
import { ref } from 'vue';

const isInfoModalOpen = ref(false);
</script>
