import { useApi } from "#/composables/useApi";
import { Guess } from "#/types/api/guess";

export function useGuessApi() {
    const { request } = useApi('/guess');

    return {
        async postGuess(guess: string, guessNumber: number) {
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