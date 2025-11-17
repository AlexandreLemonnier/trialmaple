<template>
    <div class="flex flex-col w-full gap-4 bg-card-background rounded-xl p-4">
        <span class="text-2xl font-semibold">{{ mapName }}</span>
        <div class="flex flex-wrap gap-4">
            <GuessElement label="Difficulty" :hints="[guess.difficulty]" />
            <GuessElement label="Points" :hints="[guess.points]" />
            <GuessElement label="Checkpoints" :hints="[guess.checkpoints]" />
            <GuessElement label="Finishers" :hints="[guess.nbFinishers]" />
            <GuessElement label="World Record" :hints="[guess.worldRecord]" />
            <GuessElement label="Author(s)" :hints="guess.authors" />
        </div>
    </div>
</template>

<script setup lang="ts">
import GuessElement from '#/components/GuessElement.vue';
import { Guess } from '#/types/api/guess';

defineProps<{
    mapName: string;
    guess: Guess;
}>();
</script>