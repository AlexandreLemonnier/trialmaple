<template>
    <div class="flex flex-col gap-4 items-center">
        <div v-if="imagesLoaded" class="flex gap-2 items-center text-5xl lg:text-6xl xl:text-7xl">
            <span>woho</span>
            <img :src="smirkcat" alt="smirkcat" class="h-[1em]" />
            <img :src="thumbsup" alt="thumbsup" class="h-[1em]" />
        </div>
        <div class="flex gap-2 items-center">
            <button type="button"
                    class="text-md lg:text-lg xl:text-xl rounded-sm bg-card-background border border-app-border py-1 px-2 cursor-pointer hover:scale-105 transition-transform"
                    @click="copyHistoryResult">
                Share result
            </button>
            <span v-if="resultCopyStatus !== 'NONE'"
                  class="text-sm italic"
                  :class="resultCopyClass">
                {{ resultCopyMessage }}
            </span>
        </div>
    </div>
</template>

<script setup lang="ts">
import smirkcat from '#/assets/smirkcat.png';
import thumbsup from '#/assets/thumbsup.png';
import type { DeltaHint } from '#/types/api/deltaHint';
import type { Guess } from '#/types/api/guess';
import { copyToClipboard } from '#/utils/copyToClipboard';
import { computed, onMounted, ref } from 'vue';

const { dailyMapNumber, history } = defineProps<{
    dailyMapNumber: number;
    history: Record<string, Guess>;
}>();

const imagesLoaded = ref(false);
const smirkRef = ref<HTMLImageElement | null>(null);
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
    const imgs = [smirkRef.value, thumbRef.value].filter(Boolean) as HTMLImageElement[];
    await Promise.all(imgs.map(waitForImage));
    imagesLoaded.value = true;
});

// --- Share result ---

const resultCopyStatus = ref<'NONE' | 'SUCCESS' | 'ERROR'>('NONE');

const resultCopyMessage = computed(() => {
    if (resultCopyStatus.value === 'SUCCESS') return 'Copied';
    if (resultCopyStatus.value === 'ERROR') return 'Error';
    return '';
});

const resultCopyClass = computed(() => {
    return resultCopyStatus.value === 'SUCCESS' ? 'text-confirmation-text' : 'text-error';
});

const deltaHintEmoji: Record<DeltaHint, string> = {
    EQUAL: '🟩',
    LESS: '🔻',
    MORE: '🔺'
};

function hintToEmoji(hint: boolean | DeltaHint) {
    if (typeof hint === 'boolean') {
        return hint ? '🟩' : '🟥';
    }
    return deltaHintEmoji[hint];
}

async function copyHistoryResult() {
    const guessesCount = Object.keys(history).length;
    let result = `I solved TrialMaple #${dailyMapNumber} in ${guessesCount} ${guessesCount <= 1 ? 'guess' : 'guesses'}.`;
    for (const guess of Object.values(history)) {
        const difficultyEmoji = hintToEmoji(guess.difficulty.hint);
        const pointEmoji = hintToEmoji(guess.points.hint);
        const checkpointEmoji = hintToEmoji(guess.checkpoints.hint);
        const finisherCountEmoji = hintToEmoji(guess.finisherCount.hint);
        const worldRecordEmoji = hintToEmoji(guess.worldRecord.hint);
        const authorsEmoji = hintToEmoji(guess.authors.some((hintPair) => hintPair.hint));
        result += `\n${difficultyEmoji}${pointEmoji}${checkpointEmoji}${finisherCountEmoji}${worldRecordEmoji}${authorsEmoji}`;
    }
    resultCopyStatus.value = await copyToClipboard(result) ? 'SUCCESS' : 'ERROR';
}
</script>
