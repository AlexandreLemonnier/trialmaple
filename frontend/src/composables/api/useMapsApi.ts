import { useApi } from '#/composables/useApi';
import type { BlurMap } from '#/types/api/blurMap';
import type { BlurGameMode, ClassicGameMode, GeoguessrGameMode, ZoomGameMode } from '#/types/api/gameMode';
import type { GeoguessrMap } from '#/types/api/geoguessrMap';
import type { TmMap } from '#/types/api/tmMap';
import type { ZoomMap } from '#/types/api/zoomMap';

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMaps(gameMode: ClassicGameMode) {
            return await request<TmMap[]>('/list', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        },
        async getGeoguessrMaps(gameMode: GeoguessrGameMode) {
            return await request<GeoguessrMap[]>('/geoguessr', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        },
        async getBlurMaps(gameMode: BlurGameMode) {
            return await request<BlurMap[]>('/blur', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        },
        async getZoomMaps(gameMode: ZoomGameMode) {
            return await request<ZoomMap[]>('/zoom', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        }
    };
}
