<template>
    <ClassicGame game-mode="CLASSIC_TM2020_TRIAL"
                 game-mode-display-name="TM2020 Trial Maple"
                 history-storage-key="tm2020TrialClassicHistory"
                 daily-map-uuid-storage-key="tm2020TrialClassicDailyMapUuid"
                 :hints-to-display
                 :show-login="false" />
</template>

<script setup lang="ts">
import type { HintInformation } from '#/types/HintInformation';
import ClassicGame from '#/views/ClassicGame.vue';

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
