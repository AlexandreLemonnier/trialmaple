import { useEnv } from '#/composables/useEnv';
import type { BlurGameMode, GeoguessrGameMode } from '#/types/api/gameMode';

export function usePictureApi() {
    const env = useEnv();

    return {
        getGeoguessrPictureUrl(gameMode: GeoguessrGameMode, attempt: number) {
            return `${env.PROXIED_API_URL_PREFIX}/geoguessr-picture/${attempt}?gameMode=${gameMode}`;
        },
        getBlurPictureUrl(gameMode: BlurGameMode) {
            return `${env.PROXIED_API_URL_PREFIX}/blur-picture?gameMode=${gameMode}`;
        }
    };
}
