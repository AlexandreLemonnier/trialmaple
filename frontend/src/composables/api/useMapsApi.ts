import { useApi } from '#/composables/useApi';
import type { GameMode } from '#/types/api/gameMode';
import type { TmMap } from '#/types/api/tmMap';

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMapNames(gameMode: GameMode) {
            return await request<TmMap[]>('/list', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        }
    };
}
