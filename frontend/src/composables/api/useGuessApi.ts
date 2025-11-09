import { Guess } from "#/types/api/guess";
import { useApi } from "../useApi";

export function useGuessApi() {
    const { request } = useApi('/guess');

    return {
        async postGuess(guess: string, guessNumber = 1) {
            return await request<Guess>('', {
                method: 'POST',
                query: {
                    guess: encodeURIComponent(guess),
                    guessNumber
                }
            });
        }
    }
}