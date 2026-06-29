import type { GameMode } from '#/types/api/gameMode';
import type { GameData } from '#/types/GameData';
import { gamesInfo } from '#/types/GameInfo';
import { useStorage } from '@vueuse/core';
import { defineStore } from 'pinia';
import { ref, toRefs } from 'vue';

export function createGameStore(gameMode: GameMode) {
    return defineStore(gameMode, () => {
        const gameData = useStorage<GameData>(gamesInfo[gameMode].storageKey, {
            history: {},
            dailyMapUuid: '',
            answer: null
        });
        const { history, dailyMapUuid, answer } = toRefs(gameData.value);

        const dailyMapNumber = ref<number>();
        const playersAverageScore = ref<number>();

        function isInHistory(mapId: string) {
            return Object.keys(history.value).includes(mapId);
        }

        function historyContainsSuccess() {
            return Object.values(history.value).some((guess) => guess.success);
        }

        return { history, dailyMapUuid, dailyMapNumber, playersAverageScore, isInHistory, historyContainsSuccess, answer };
    });
}
