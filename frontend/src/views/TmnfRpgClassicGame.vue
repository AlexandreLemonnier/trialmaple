<template>
    <ClassicGame game-mode="CLASSIC_TMNF_RPG"
                 history-storage-key="tmnfRpgClassicHistory"
                 daily-map-uuid-storage-key="tmnfRpgClassicDailyMapUuid"
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
