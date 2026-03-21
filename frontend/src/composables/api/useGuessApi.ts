import { useApi } from '#/composables/useApi';
import type { GameMode } from '#/types/api/gameMode';
import type { Guess } from '#/types/api/guess';

export function useGuessApi() {
    const { request } = useApi('/guess');

    return {
        async postGuess(gameMode: GameMode, guessedMapUuid: string, guessNumber: number, dailyMapUuid: string) {
            return await request<Guess>('', {
                method: 'POST',
                body: {
                    guessedMapUuid,
                    guessNumber,
                    dailyMapUuid
                },
                query: {
                    gameMode
                }
            });
        },
        async getDailyMapUuid(gameMode: GameMode) {
            return await request<string>('/daily-map', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        }
    };
}
