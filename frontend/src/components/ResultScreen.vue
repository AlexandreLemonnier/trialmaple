<template>
    <Transition name="fade" appear>
        <div class="flex flex-col gap-4 items-center p-2 lg:p-4 border-2 border-app-border rounded-lg" :class="hasWon ? 'bg-win-background' : 'bg-lose-background'">
            <div v-if="imagesLoaded" class="flex gap-2 items-center text-5xl lg:text-6xl xl:text-7xl">
                <template v-if="hasWon">
                    <span>woho</span>
                    <img :src="smirkcat" alt="smirkcat" class="h-[1em]" />
                    <img :src="thumbsup" alt="thumbsup" class="h-[1em]" />
                </template>
                <template v-else>
                    <img :src="crycat" alt="crycat" class="h-[1em]" />
                </template>
            </div>
            <span v-html="resultMessage"></span>
            <slot name="shareButton"></slot>
        </div>
    </Transition>
</template>

<script setup lang="ts">
import crycat from '#/assets/crycat.png';
import smirkcat from '#/assets/smirkcat.png';
import thumbsup from '#/assets/thumbsup.png';
import { createGameStore } from '#/stores/gameStore';
import type { Answer } from '#/types/api/answer';
import type { GameMode } from '#/types/api/gameMode';
import { storeToRefs } from 'pinia';
import { computed, onMounted, ref } from 'vue';

const { hasWon, answer, gameMode, gameModeDisplayName, storageKey } = defineProps<{
    hasWon: boolean;
    answer: Answer | null;
    gameMode: GameMode;
    gameModeDisplayName: string;
    storageKey: string;
}>();

const gameStore = createGameStore(gameMode, storageKey)();
const { history, dailyMapNumber, playersAverageScore } = storeToRefs(gameStore);

const resultMessage = computed(() => {
    let message = '';
    if (hasWon) {
        message += `You solved ${gameModeDisplayName} #${dailyMapNumber.value} in <strong>${Object.keys(history.value).length}</strong> guesses! 🎉`;
    } else if (answer) {
        message += `The correct answer was <strong>${answer.mapName}</strong>.`;
    }
    return `${message} Other players needed an average of ${playersAverageScore.value} guesses.`;
});

const imagesLoaded = ref(false);
const smirkRef = ref<HTMLImageElement | null>(null);
const cryRef = ref<HTMLImageElement | null>(null);
const thumbRef = ref<HTMLImageElement | null>(null);

async function waitForImage(img: HTMLImageElement): Promise<void> {
    return new Promise((resolve) => {
        if (img.complete) {
            resolve();
        } else {
            img.addEventListener('load', () => resolve(), { once: true });
            img.addEventListener('error', () => resolve(), { once: true });
        }
    });
}

onMounted(async () => {
    const imgs = [hasWon ? smirkRef.value : cryRef.value, thumbRef.value].filter(Boolean) as HTMLImageElement[];
    await Promise.all(imgs.map(waitForImage));
    imagesLoaded.value = true;
});

</script>
