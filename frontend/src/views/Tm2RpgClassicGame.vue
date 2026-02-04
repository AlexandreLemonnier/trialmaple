<template>
    <ClassicGame game-mode="CLASSIC_TM2_RPG"
                 game-mode-display-name="TM2 RPG Maple"
                 history-storage-key="tm2RpgClassicHistory"
                 daily-map-uuid-storage-key="tm2RpgClassicDailyMapUuid"
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
        guessProp: 'authors'
    },
    {
        label: 'Release year',
        guessProp: 'releaseYear'
    }
];

</script>
