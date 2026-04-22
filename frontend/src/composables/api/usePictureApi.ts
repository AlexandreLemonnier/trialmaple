import { useEnv } from '#/composables/useEnv';
import type { GameMode } from '#/types/api/gameMode';

export function usePictureApi() {
    const env = useEnv();

    return {
        getPictureUrl(gameMode: GameMode, attempt: number) {
            return `${env.PROXIED_API_URL_PREFIX}/picture/${attempt}?gameMode=${gameMode}`;
        }
    };
}
