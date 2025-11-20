<template>
    <div class="flex flex-col w-full gap-4 bg-card-background rounded-xl p-4">
        <span class="text-2xl font-semibold">{{ mapName }}</span>

        <TransitionGroup name="fade" tag="div" class="flex flex-wrap gap-4">
            <GuessElement
                v-for="item in displayedElements"
                :key="item.label"
                :label="item.label"
                :hints="item.hints"
            />
        </TransitionGroup>
    </div>
</template>

<script setup lang="ts">
import GuessElement from '#/components/GuessElement.vue';
import { Guess } from '#/types/api/guess';
import { onMounted, ref } from 'vue';

const props = defineProps<{
    mapName: string;
    guess: Guess;
}>();

const emit = defineEmits <{
    (e: 'animation-finished'): void
}>();

const delay = 300;

const elementsToDisplay = [
    { label: 'Difficulty', hints: [props.guess.difficulty] },
    { label: 'Points', hints: [props.guess.points] },
    { label: 'Checkpoints', hints: [props.guess.checkpoints] },
    { label: 'Finishers', hints: [props.guess.nbFinishers] },
    { label: 'World Record', hints: [props.guess.worldRecord] },
    { label: 'Author(s)', hints: props.guess.authors }
];

const displayedElements = ref<typeof elementsToDisplay>([]);

// Add elements progressively to make a sequential animation
onMounted(async () => {
    for (let element of elementsToDisplay) {
        displayedElements.value.push(element);
        await new Promise(resolve => setTimeout(resolve, delay));
    }
    emit('animation-finished');
});
</script>
