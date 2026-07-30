import { useAdminApi } from '#/composables/api/useAdminApi';
import type { GameMode } from '#/types/api/gameMode';
import type { CreateTmMap, TmMap } from '#/types/api/tmMap';

export function useAdminMapsApi() {
    const { adminRequest } = useAdminApi('/maps');

    return {
        async getMaps(gameMode: GameMode) {
            return await adminRequest<TmMap[]>('', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        },
        async updateMaps(maps: TmMap[]) {
            return await adminRequest('', {
                method: 'PUT',
                body: maps
            });
        },
        async createMap(map: CreateTmMap, gameMode: GameMode) {
            return await adminRequest('', {
                method: 'POST',
                query: {
                    gameMode
                },
                body: map
            });
        }
    };
}
