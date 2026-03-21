import type { Guess } from '#/types/api/guess';
import type { TmMap } from '#/types/api/tmMap';
import { useStorage } from '@vueuse/core';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export function createGameStore(storeId: string, historyStorageKey: string, dailyMapUuidStorageKey: string) {
    return defineStore(storeId, () => {
        const history = useStorage<Record<string, Guess>>(historyStorageKey, {});
        const dailyMapUuid = useStorage<string>(dailyMapUuidStorageKey, '');
        const dailyMapNumber = ref<number>();
        const playersAverageScore = ref<number>();

        function isMapInHistory(map: TmMap) {
            return Object.keys(history.value).includes(map.uuid);
        }

        return { history, dailyMapUuid, dailyMapNumber, playersAverageScore, isMapInHistory };
    });
}
