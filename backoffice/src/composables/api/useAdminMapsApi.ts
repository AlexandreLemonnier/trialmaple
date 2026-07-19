import { useAdminApi } from '#/composables/api/useAdminApi';
import type { GameMode } from '#/types/api/gameMode';
import type { TmnfTrialMap } from '#/types/api/tmmap/tmnfTrialMap';

export function useAdminMapsApi() {
    const { adminRequest } = useAdminApi('/maps');

    return {
        async getTmnfTrialMaps() {
            return await adminRequest<TmnfTrialMap[]>('', {
                method: 'GET',
                query: {
                    gameMode: 'CLASSIC_TMNF_TRIAL' satisfies GameMode
                }
            });
        }
    };
}
