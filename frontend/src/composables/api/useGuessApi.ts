import { useApi } from '#/composables/useApi';
import type { Answer } from '#/types/api/answer';
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
        async giveUp(gameMode: GameMode) {
            return await request<Answer>('/giveup', {
                method: 'POST',
                query: {
                    gameMode
                }
            });
        }
    };
}
