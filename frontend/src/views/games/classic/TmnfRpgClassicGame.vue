<template>
    <ClassicGame game-mode="CLASSIC_TMNF_RPG"
                 game-mode-display-name="TMNF RPG Maple"
                 storage-key="tmnfRpgClassic"
                 :hints-to-display />
</template>

<script setup lang="ts">
import type { HintInformation } from '#/types/HintInformation';
import ClassicGame from '#/views/games/classic/ClassicGame.vue';

const hintsToDisplay: HintInformation[] = [
    {
        label: 'Stars',
        tooltip: 'From 1 to 7, how competitive is the map',
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
