import { useApi } from "#/composables/useApi";
import { Guess } from "#/types/api/guess";

export function useGuessApi() {
    const { request } = useApi('/guess');

    return {
        async postGuess(guess: string, guessNumber = 1) {
            return await request<Guess>('', {
                method: 'POST',
                query: {
                    guess: guess,
                    guessNumber
                }
            });
        }
    }
}