<template>
    <ClassicGame game-mode="CLASSIC_TMNF_TRIAL"
                 game-mode-display-name="TMNF Trial Maple"
                 history-storage-key="tmnfTrialClassicHistory"
                 daily-map-uuid-storage-key="tmnfTrialClassicDailyMapUuid"
                 :hints-to-display />
</template>

<script setup lang="ts">
import type { HintInformation } from '#/types/HintInformation';
import ClassicGame from '#/views/ClassicGame.vue';

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
        label: 'World Record',
        guessProp: 'wrTime'
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
