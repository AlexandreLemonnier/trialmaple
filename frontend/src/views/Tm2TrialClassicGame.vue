<template>
    <ClassicGame game-mode="CLASSIC_TM2_TRIAL"
                 game-mode-display-name="TM2 Trial Maple"
                 history-storage-key="tm2TrialClassicHistory"
                 daily-map-uuid-storage-key="tmTrialClassicDailyMapUuid"
                 :hints-to-display />
</template>

<script setup lang="ts">
import type { Guess } from '#/types/api/guess';
import ClassicGame from '#/views/ClassicGame.vue';

const hintsToDisplay: { label: string, guessProp: keyof Guess }[] = [
    {
        label: 'Stars',
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
