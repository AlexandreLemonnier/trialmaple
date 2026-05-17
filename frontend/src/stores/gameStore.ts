import type { GameData } from '#/types/GameData';
import { useStorage } from '@vueuse/core';
import { defineStore } from 'pinia';
import { ref, toRefs } from 'vue';

export function createGameStore(storeId: string, storageKey: string) {
    return defineStore(storeId, () => {
        const gameData = useStorage<GameData>(storageKey, {
            history: {},
            dailyMapUuid: '',
            answer: null
        });
        const { history, dailyMapUuid, answer } = toRefs(gameData.value);

        const dailyMapNumber = ref<number>();
        const playersAverageScore = ref<number>();

        function isInHistory(mapId: string) {
            return Object.keys(history).includes(mapId);
        }

        function historyContainsSuccess() {
            return Object.values(history.value).some((guess) => guess.success);
        }

        return { history, dailyMapUuid, dailyMapNumber, playersAverageScore, isInHistory, historyContainsSuccess, answer };
    });
}
