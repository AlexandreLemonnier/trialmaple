import { useAdminApi } from '#/composables/api/useAdminApi';
import type { GameMode } from '#/types/api/gameMode';
import type { CreateTmMap, TmMap } from '#/types/api/tmmap/tmMap';

export function useAdminMapsApi() {
    const { adminRequest } = useAdminApi('/maps');

    return {
        async getMaps<T extends TmMap>(gameMode: GameMode) {
            return await adminRequest<T[]>('', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        },
        async updateMaps<T extends TmMap>(maps: T[]) {
            return await adminRequest('', {
                method: 'PUT',
                body: maps
            });
        },
        async createMap<T extends CreateTmMap>(map: T, gameMode: GameMode) {
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
