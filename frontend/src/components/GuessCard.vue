<template>
    <div class="flex flex-col w-full gap-4 bg-card-background rounded-xl p-4 shadow-lg">
        <span class="text-2xl font-semibold">{{ mapName }}</span>

        <TransitionGroup name="fade" tag="div" class="flex flex-wrap gap-4">
            <GuessElement v-for="item in displayedElements"
                          :key="item.label"
                          :label="item.label"
                          :hints="item.hints" />
        </TransitionGroup>
    </div>
</template>

<script setup lang="ts">
import GuessElement from '#/components/GuessElement.vue';
import type { DeltaHint } from '#/types/api/deltaHint';
import type { Guess, HintPair, WrHolder } from '#/types/api/guess';
import { toArray } from '#/utils/toArray';
import { onMounted, ref } from 'vue';

const { mapName, guess, hintsToDisplay, ignoreAnimations } = defineProps<{
    mapName: string;
    guess: Guess;
    hintsToDisplay: { label: string, guessProp: keyof Guess }[];
    ignoreAnimations?: boolean;
}>();

const emit = defineEmits<(e: 'animationFinished') => void>();

const delay = 300;

const displayedElements = ref<{
    label: string,
    hints: HintPair<string | number | WrHolder, boolean | DeltaHint>[]
}[]>([]);

// Add elements progressively to make a sequential animation
onMounted(async () => {
    for (const hint of hintsToDisplay) {
        const guessElement = guess[hint.guessProp];
        if (guessElement && typeof guessElement !== 'boolean') {
            displayedElements.value.push(
                {
                    label: hint.label,
                    hints: toArray(guessElement)
                }
            );
            if (!ignoreAnimations) {
                await new Promise((resolve) => setTimeout(resolve, delay));
            }
        }
    }
    emit('animationFinished');
});
</script>
