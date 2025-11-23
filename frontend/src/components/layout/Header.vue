<template>
    <header class="sticky flex justify-between items-center px-5 w-full h-16 bg-header-background tracking-wide">
        <div class="flex items-center gap-2 select-none">
            <img :src="TrackManiaLogo" alt="TrialMaple logo" class="h-[clamp(2rem,4vw,4rem)]" />
            <div class="flex">
                <h1 class="text-[clamp(1.25rem,2.5vw,1.75rem)] font-bold">T</h1>
                <h1 class="text-[clamp(1rem,2vw,1.5rem)] font-semibold">rial</h1>
                <h1 class="text-[clamp(1.25rem,2.5vw,1.75rem)] font-bold">M</h1>
                <h1 class="text-[clamp(1rem,2vw,1.5rem)] font-semibold">ap</h1>
                <h1 class="text-[clamp(0.9rem,1.5vw,1.25rem)] font-semibold">le</h1>
            </div>
        </div>
        <Icon class="cursor-pointer" name="circle-info" size="md" @click="open = true" />
    </header>
    <div v-if="open" class="fixed inset-0 z-9999 flex items-center justify-center bg-black/60 p-4" @click.self="open = false">
        <div class="bg-neutral-900 text-neutral-100 rounded-2xl p-5 max-w-md w-full border border-white/10 shadow-xl">
            <div class="flex items-start justify-between gap-4">
                <h2 class="text-lg font-black">About TrialMaple</h2>
                <Icon class="cursor-pointer" name="x" size="sm" @click="open = false" />
            </div>
            <p class="mt-3 text-sm text-neutral-300">Guess today's Trackmania trial map. More actions coming soon in this header.</p>
        </div>
    </div>
</template>

<script setup lang="ts">
import TrackManiaLogo from '#/assets/TrackMania-Logo.svg';
import Icon from '#/components/Icon.vue';
import { ref } from 'vue';
const open = ref(false);
</script>
