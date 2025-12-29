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
import type { Guess } from '#/types/api/guess';
import { onMounted, ref } from 'vue';

const { mapName, guess, ignoreAnimations } = defineProps<{
    mapName: string;
    guess: Guess;
    ignoreAnimations?: boolean;
}>();

const emit = defineEmits<(e: 'animationFinished') => void>();

const delay = 300;

const elementsToDisplay = [
    { label: 'Difficulty', hints: [guess.difficulty] },
    { label: 'Points', hints: [guess.points] },
    { label: 'Checkpoints', hints: [guess.checkpoints] },
    { label: 'Finishers', hints: [guess.finisherCount] },
    { label: 'World Record', hints: [guess.wrTime] },
    { label: 'Author(s)', hints: guess.authors },
    { label: 'Release year', hints: [guess.releaseYear] }
];

const displayedElements = ref<typeof elementsToDisplay>([]);

// Add elements progressively to make a sequential animation
onMounted(async () => {
    if (ignoreAnimations) {
        displayedElements.value = elementsToDisplay;
    } else {
        for (const element of elementsToDisplay) {
            displayedElements.value.push(element);
            await new Promise((resolve) => setTimeout(resolve, delay));
        }
    }
    emit('animationFinished');
});
</script>
