<template>
    <span class="text-sm italic">Based on <a :href="mapLists[gameMode].url" target="_blank"><u>{{ mapLists[gameMode].name }}</u></a> list</span>
</template>

<script setup lang="ts">
import type { GameMode } from '#/types/api/gameMode';
import { ExternalMapList } from '#/types/ExternalMapList';

const { gameMode } = defineProps<{
    gameMode: GameMode;
}>();

const mapLists: Record<GameMode, {
    url: ExternalMapList,
    name: string
}> = {
    CLASSIC_TMNF_TRIAL: {
        url: ExternalMapList.TMNF_TRIAL_LIST,
        name: 'TMNF Hardest Trials'
    },
    CLASSIC_TMNF_RPG: {
        url: ExternalMapList.TMNF_RPG_LIST,
        name: 'TMNF Classic RPG'
    }
};
</script>
