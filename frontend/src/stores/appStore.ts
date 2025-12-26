import type { Guess } from '#/types/api/guess';
import { useStorage } from '@vueuse/core';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAppStore = defineStore('app', () => {
    const history = useStorage<Record<string, Guess>>('history', {});
    const dailyMapNumber = ref<number>();
    const playersAverageScore = ref<number>();

    function isMapInHistory(mapName: string) {
        return Object.keys(history.value).includes(mapName);
    }

    return { history, dailyMapNumber, playersAverageScore, isMapInHistory };
});
