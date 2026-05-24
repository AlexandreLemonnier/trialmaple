<template>
    <span class="text-sm italic">Based on <a :href="mapList.url" target="_blank"><u>{{ mapList.name }}</u></a> list</span>
</template>

<script setup lang="ts">
import type { ClassicGameMode } from '#/types/api/gameMode';
import { getMapList } from '#/utils/getMapList';
import { computed } from 'vue';

const { gameMode } = defineProps<{
    gameMode: ClassicGameMode;
}>();

const mapList = computed(() => getMapList(gameMode));

</script>
