import { useApi } from '#/composables/useApi';
import type { Guess } from '#/types/api/guess';

export function useGuessApi() {
    const { request } = useApi('/guess');

    return {
        async postGuess(guess: string, guessNumber: number) {
            return await request<Guess>('', {
                method: 'POST',
                query: {
                    guess,
                    guessNumber
                }
            });
        }
    };
}
