import { useApi } from '#/composables/useApi';
import type { DailyStats } from '#/types/api/dailyStats';
import type { GameMode } from '#/types/api/gameMode';

export function useDailyStatsApi() {
    const { request } = useApi('/daily-stats');

    return {
        async getDailyStats(gameMode: GameMode) {
            return await request<DailyStats>('', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        }
    };
}
