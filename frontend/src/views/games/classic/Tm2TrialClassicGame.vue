<template>
    <ClassicGame game-mode="CLASSIC_TM2_TRIAL"
                 :hints-to-display />
</template>

<script setup lang="ts">
import type { HintInformation } from '#/types/HintInformation';
import ClassicGame from '#/views/games/classic/ClassicGame.vue';

const hintsToDisplay: HintInformation[] = [
    {
        label: 'Stars',
        tooltip: 'Difficulty rating',
        guessProp: 'points'
    },
    {
        label: 'Checkpoints',
        guessProp: 'checkpoints'
    },
    {
        label: 'WR Time',
        guessProp: 'wrTime'
    },
    {
        label: 'WR Holder',
        guessProp: 'wrHolder'
    },
    {
        label: 'WR Year',
        guessProp: 'wrYear'
    },
    {
        label: 'Author(s)',
        guessProp: 'authors'
    },
    {
        label: 'Release year',
        guessProp: 'releaseYear'
    }
];

</script>
