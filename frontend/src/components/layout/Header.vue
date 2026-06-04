<template>
    <header class="sticky flex justify-between items-center px-2 lg:px-5 w-full h-10 lg:h-14 bg-header-background tracking-wide">
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
            <Button class="hidden lg:flex bg-discord/80 text-primary-white"
                    label="Discord"
                    icon-name="discord"
                    icon-size="md"
                    icon-position="right"
                    @click="isDiscordModalOpen = true" />
            <Icon class="block lg:hidden text-discord cursor-pointer" name="discord" size="md" @click="isDiscordModalOpen = true" />
            <LoginButton />
        </div>
    </header>
    <InfoModal v-model="isInfoModalOpen" />
    <DiscordModal v-model="isDiscordModalOpen" />
</template>

<script setup lang="ts">
import TrackManiaLogo from '#/assets/TrackMania-Logo.svg?component';
import Button from '#/components/Button.vue';
import Icon from '#/components/Icon.vue';
import ThemeToggle from '#/components/layout/ThemeToggle.vue';
import DiscordModal from '#/components/modal/DiscordModal.vue';
import InfoModal from '#/components/modal/InfoModal.vue';
import { Route } from '#/router/Route';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import LoginButton from './LoginButton.vue';

const router = useRouter();

const isInfoModalOpen = ref(false);
const isDiscordModalOpen = ref(false);
</script>
