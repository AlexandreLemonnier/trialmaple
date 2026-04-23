<template>
    <div class="flex flex-col gap-8 items-center mt-2 lg:mt-4">
        <h1 class="text-3xl lg:text-4xl font-bold">Trackmania Maple</h1>
        <h2 class="text-lg lg:text-2xl">Choose a game mode and find today's map!</h2>
        <div class="flex flex-col lg:flex-wrap xl:flex-row xl:flex-nowrap gap-4 items-center">
            <!-- Game Card -->
            <div v-for="(game) in games"
                 :key="game.title"
                 class="flex flex-col gap-2 w-full rounded-md border border-app-border"
                 :style="{ background: `linear-gradient(to bottom, ${game.colorGradient.start}40, ${game.colorGradient.end}40)` }">
                <div class="flex items-center gap-2 lg:gap-3 px-2 lg:px-3 py-1 lg:py-1.5 rounded-lg font-semibold">
                    <img :src="game.icon" :alt="game.title" class="w-4 h-4 lg:w-5 lg:h-5 object-contain" />
                    <h3>{{ game.title }}</h3>
                </div>
                <!-- Sub styles (Trial, RPG) -->
                <div class="flex gap-2 w-full">
                    <div v-for="style in game.styles"
                         :key="style.title"
                         class="flex flex-col items-center w-full gap-2 lg:gap-3 px-2 lg:px-3 py-1.5 lg:py-2.5">
                        <h3 class="text-sm lg:text-base font-semibold italic">{{ style.title }}</h3>
                        <div class="flex flex-col gap-1">
                            <button v-for="gameMode in style.gameModes"
                                    :key="gameMode.title"
                                    type="button"
                                    class="flex items-center gap-1.5 lg:gap-2 text-sm lg:text-base rounded-full border-2 border-app-border py-1 px-3 bg-share-button cursor-pointer hover:scale-105 transition-transform"
                                    @click="navigateToRoute(gameMode.routeName)">
                                <Icon class="text-error" :name="gameMode.icon" size="sm" />
                                <span class="hidden xs:block">{{ gameMode.title }}</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import tm2Icon from '#/assets/tm2.png';
import tm2020Icon from '#/assets/tm2020.jpg';
import tmnfIcon from '#/assets/tmnf.jpg';
import Icon from '#/components/Icon.vue';
import { Route } from '#/router/Route';
import type { IconName } from '#/types/IconName';
import { useRouter } from 'vue-router';

const router = useRouter();

async function navigateToRoute(routeName: Route) {
    await router.push({ name: routeName });
}

type Game = {
    title: string;
    icon: string;
    colorGradient: {
        start: string;
        end: string;
    };
    styles: {
        title: string;
        gameModes: {
            title: string;
            icon: IconName;
            routeName: Route;
        }[]
    }[]
};

const games: Game[] = [
    {
        title: 'TM2020',
        icon: tm2020Icon,
        colorGradient: {
            start: '#4dc3e7',
            end: '#5abc89'
        },
        styles: [
            {
                title: 'Trial',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TM2020_TRIAL_CLASSIC_MODE
                    }
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    {
                        title: 'Geoguessr',
                        icon: 'image',
                        routeName: Route.TM2020_RPG_GEOGUESSR_MODE
                    }
                ]
            }
        ]
    },
    {
        title: 'TM2',
        icon: tm2Icon,
        colorGradient: {
            start: '#155696',
            end: '#55a4df'
        },
        styles: [
            {
                title: 'Trial',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TM2_TRIAL_CLASSIC_MODE
                    }
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TM2_RPG_CLASSIC_MODE
                    }
                ]
            }
        ]
    },
    {
        title: 'TMNF',
        icon: tmnfIcon,
        colorGradient: {
            start: '#316a19',
            end: '#5fb900'
        },
        styles: [
            {
                title: 'Trial',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TMNF_TRIAL_CLASSIC_MODE
                    }
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TMNF_RPG_CLASSIC_MODE
                    }
                ]
            }
        ]
    }
];
</script>
