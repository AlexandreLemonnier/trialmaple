<template>
    <ClassicGame game-mode="CLASSIC_TMNF_TRIAL"
                 :hints-to-display
                 :show-login="false" />
</template>

<script setup lang="ts">
import type { HintInformation } from '#/types/HintInformation';
import ClassicGame from '#/views/games/classic/ClassicGame.vue';

const hintsToDisplay: HintInformation[] = [
    {
        label: 'Difficulty',
        guessProp: 'difficulty'
    },
    {
        label: 'Points',
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
        label: 'Author(s)',
        guessProp: 'authors'
    },
    {
        label: 'Release year',
        guessProp: 'releaseYear'
    }
];

</script>
