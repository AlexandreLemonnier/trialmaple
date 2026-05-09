<template>
    <ClassicGame game-mode="CLASSIC_TM2_RPG"
                 game-mode-display-name="TM2 RPG Maple"
                 storage-key="tm2RpgClassic"
                 :hints-to-display />
</template>

<script setup lang="ts">
import type { HintInformation } from '#/types/HintInformation';
import ClassicGame from '#/views/ClassicGame.vue';

const hintsToDisplay: HintInformation[] = [
    {
        label: 'Classic',
        tooltip: 'Classic maps are the most iconic ones',
        guessProp: 'classic'
    },
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
        tooltip: 'Authors with a slight contribution like cutfix are not included',
        guessProp: 'authors'
    },
    {
        label: 'Original release year',
        tooltip: 'Release year of the original map (not the cutfix/v2/+/mp4 version)',
        guessProp: 'releaseYear'
    }
];

</script>
