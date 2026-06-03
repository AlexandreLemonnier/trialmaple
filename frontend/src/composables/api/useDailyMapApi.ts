import { useApi } from '#/composables/useApi';
import type { GameMode } from '#/types/api/gameMode';

export function useDailyMapApi() {
    const { request } = useApi('/daily-map');

    return {
        async getDailyMapUuid(gameMode: GameMode) {
            return await request<string>('/uuid', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        }
    };
}
