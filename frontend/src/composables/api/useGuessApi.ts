import { useApi } from '#/composables/useApi';
import type { Guess } from '#/types/api/guess';

export function useGuessApi() {
    const { request } = useApi('/guess');

    return {
        async postGuess(guess: string, guessNumber: number, dailyMapUuid: string) {
            return await request<Guess>('', {
                method: 'POST',
                body: {
                    guess,
                    guessNumber,
                    dailyMapUuid
                }
            });
        },
        async getDailyMapUuid() {
            return await request<string>('/daily-map', {
                method: 'GET'
            });
        }
    };
}
