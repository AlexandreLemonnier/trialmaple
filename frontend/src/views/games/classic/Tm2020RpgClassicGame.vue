<template>
    <ClassicGame game-mode="CLASSIC_TM2020_RPG"
                 storage-key="tm2020RpgClassic"
                 :hints-to-display
                 :show-login="false" />
</template>

<script setup lang="ts">
import type { HintInformation } from '#/types/HintInformation';
import ClassicGame from '#/views/games/classic/ClassicGame.vue';

const hintsToDisplay: HintInformation[] = [
    {
        label: 'Stars',
        tooltip: 'From 3 to 7, how competitive is the map',
        guessProp: 'points'
    },
    {
        label: 'Checkpoints',
        guessProp: 'checkpoints'
    },
    {
        label: 'Finishers',
        guessProp: 'finisherCount'
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
