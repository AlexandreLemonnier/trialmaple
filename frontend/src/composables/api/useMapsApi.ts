import { useApi } from '#/composables/useApi';
import type { GameMode } from '#/types/api/gameMode';
import type { GeoguessrMap } from '#/types/api/geoguessrMap';
import type { TmMap } from '#/types/api/tmMap';

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMaps(gameMode: GameMode) {
            return await request<TmMap[]>('/list', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        },
        async getGeoguessrMaps(gameMode: GameMode) {
            return await request<GeoguessrMap[]>('/geoguessr', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        }
    };
}
