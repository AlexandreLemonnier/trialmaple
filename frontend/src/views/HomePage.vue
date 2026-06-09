<template>
    <div class="flex flex-col gap-8 lg:gap-12 items-center mt-2 lg:mt-4">
        <h1 class="text-3xl lg:text-4xl font-bold">- Trackmania Maple -</h1>
        <h2 class="text-lg lg:text-2xl">Choose a game mode and find today's map!</h2>
        <div class="flex flex-col xl:flex-row gap-4 items-center">
            <!-- Game Card -->
            <div v-for="(game) in games"
                 :key="game.title"
                 class="flex flex-col gap-2 w-full h-full rounded-md border border-app-border"
                 :style="{ background: `linear-gradient(to bottom, ${game.colorGradient.start}40, ${game.colorGradient.end}40)` }">
                <div class="flex items-center gap-2 lg:gap-3 px-2 lg:px-3 py-1 lg:py-1.5 rounded-lg font-semibold">
                    <img :src="game.icon" :alt="game.title" class="w-4 h-4 lg:w-5 lg:h-5 object-contain" />
                    <h3>{{ game.title }}</h3>
                </div>
                <!-- Sub styles (Trial, RPG) -->
                <div class="flex w-full">
                    <div v-for="style in game.categories"
                         :key="style.title"
                         class="flex flex-col items-center w-full gap-2 lg:gap-3 px-2 lg:px-3 py-1.5 lg:py-2.5">
                        <h3 class="text-sm lg:text-base font-semibold italic">{{ style.title }}</h3>
                        <div class="flex flex-col gap-2 lg:gap-3">
                            <Button v-for="gameMode in style.gameModes"
                                    :key="gameMode.title"
                                    pill
                                    scale
                                    :label="gameMode.title"
                                    :icon-name="gameMode.icon"
                                    icon-class="text-error"
                                    @click="navigateToRoute(gameMode.routeName)" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modes description -->
        <div class="w-fit bg-card-background border border-app-border rounded-xl p-3 lg:p-5">
            <h3 class="flex items-center gap-2 font-semibold text-lg lg:text-xl mb-4">
                <Icon name="circle-info" size="md" />
                <span>How it works</span>
            </h3>

            <div class="flex flex-wrap gap-6 md:gap-12 text-nowrap">
                <div v-for="description in gameDescriptions" :key="description.title" class="flex-1 flex flex-col items-center gap-1">
                    <span class="font-semibold flex items-center gap-2">
                        <Icon class="text-error" :name="description.icon" size="sm" />
                        <span>{{ description.title }}</span>
                    </span>
                    <p class="text-sm lg:text-base opacity-75">
                        {{ description.description }}
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import Icon from '#/components/Icon.vue';
import type { Route } from '#/router/Route';
import { games } from '#/types/Game';
import type { IconName } from '#/types/IconName';
import { useRouter } from 'vue-router';

const router = useRouter();

async function navigateToRoute(routeName: Route) {
    await router.push({ name: routeName });
}

type GameDescription = {
    title: string;
    icon: IconName;
    description: string;
};

const gameDescriptions: GameDescription[] = [
    {
        title: 'Classic mode',
        icon: 'triangle',
        description: 'Get hints on every try.'
    },
    {
        title: 'Geoguessr mode',
        icon: 'image',
        description: 'Get up to 3 pictures.'
    },
    {
        title: 'Blurred mode',
        icon: 'focus',
        description: 'Get a blurry picture.'
    },
    {
        title: 'Zoomed mode',
        icon: 'search',
        description: 'Get a zoomed-in picture.'
    }
];
</script>
