import { useApi } from '#/composables/useApi';
import type { GameMode } from '#/types/api/gameMode';

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMapNames(gameMode: GameMode) {
            return await request<string[]>('/list', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        }
    };
}
