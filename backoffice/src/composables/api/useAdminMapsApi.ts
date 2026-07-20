import { useAdminApi } from '#/composables/api/useAdminApi';
import type { GameMode } from '#/types/api/gameMode';
import type { TmMap } from '#/types/api/tmmap/tmMap';

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
        }
    };
}
