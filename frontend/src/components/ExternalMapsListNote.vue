<template>
    <span class="text-sm italic">Based on <a :href="mapList.url" target="_blank"><u>{{ mapList.name }}</u></a> list</span>
</template>

<script setup lang="ts">
import type { GameMode } from '#/types/api/gameMode';
import { ExternalMapList } from '#/types/ExternalMapList';
import { computed } from 'vue';

const { gameMode } = defineProps<{
    gameMode: GameMode;
}>();

const MAP_LISTS: Record<GameMode, {
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

const mapList = computed(() => MAP_LISTS[gameMode]);

</script>
