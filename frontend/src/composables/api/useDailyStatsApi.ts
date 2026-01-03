import { useApi } from '#/composables/useApi';
import type { DailyStats } from '#/types/api/dailyStats';

export function useDailyStatsApi() {
    const { request } = useApi('/daily-stats');

    return {
        async getDailyStats() {
            return await request<DailyStats>('', {
                method: 'GET',
                query: {
                    gameMode: 'CLASSIC_TMNF_TRIAL'
                }
            });
        }
    };
}
