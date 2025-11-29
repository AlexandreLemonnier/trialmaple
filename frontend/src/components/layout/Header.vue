<template>
    <header class="sticky flex justify-between items-center px-5 w-full h-12 lg:h-16 bg-header-background tracking-wide">
        <div class="flex items-center gap-2 select-none">
            <img :src="TrackManiaLogo" alt="TrialMaple logo" class="h-[clamp(2rem,4vw,4rem)]" />
            <div class="flex">
                <h1 class="text-[clamp(1.5rem,2.5vw,1.75rem)] font-bold">T</h1>
                <h1 class="text-[clamp(1.25rem,2vw,1.5rem)] font-semibold">rial</h1>
                <h1 class="text-[clamp(1.5rem,2.5vw,1.75rem)] font-bold">M</h1>
                <h1 class="text-[clamp(1.25rem,2vw,1.5rem)] font-semibold">ap</h1>
                <h1 class="text-[clamp(1.15rem,1.5vw,1.25rem)] font-semibold">le</h1>
            </div>
        </div>
        <Icon class="cursor-pointer" name="circle-info" size="md" @click="isInfoModalOpen = true" />
    </header>
    <InfoModal v-model="isInfoModalOpen">
        <p class="mt-3 text-sm text-neutral-300">Guess today's Trackmania trial map. More actions coming soon in this header.</p>
    </InfoModal>
</template>

<script setup lang="ts">
import TrackManiaLogo from '#/assets/TrackMania-Logo.svg';
import Icon from '#/components/Icon.vue';
import { ref } from 'vue';
import InfoModal from '../modal/InfoModal.vue';
const isInfoModalOpen = ref(false);
</script>
