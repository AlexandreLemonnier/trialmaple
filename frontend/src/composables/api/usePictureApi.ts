import { useEnv } from '#/composables/useEnv';
import type { GeoguessrGameMode } from '#/types/api/gameMode';

export function usePictureApi() {
    const env = useEnv();

    return {
        getPictureUrl(gameMode: GeoguessrGameMode, attempt: number) {
            return `${env.PROXIED_API_URL_PREFIX}/picture/${attempt}?gameMode=${gameMode}`;
        }
    };
}
