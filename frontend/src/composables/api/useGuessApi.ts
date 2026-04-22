import { useApi } from '#/composables/useApi';
import type { GameMode } from '#/types/api/gameMode';
import type { Guess } from '#/types/api/guess';
import type { GuessRequest } from '#/types/api/guessRequest';

export function useGuessApi() {
    const { request } = useApi('/guess');

    return {
        async postGuess(gameMode: GameMode, guessRequest: GuessRequest) {
            return await request<Guess>('', {
                method: 'POST',
                body: guessRequest,
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
