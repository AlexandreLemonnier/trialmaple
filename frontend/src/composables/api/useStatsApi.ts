import { useApi } from '#/composables/useApi';
import type { DailyStats } from '#/types/api/dailyStats';
import type { GameMode } from '#/types/api/gameMode';
import type { UserStats } from '#/types/api/userStats';

export function useStatsApi() {
    const { request } = useApi('');

    return {
        async getDailyStats(gameMode: GameMode) {
            return await request<DailyStats>('/daily-stats', {
                method: 'GET',
                query: {
                    gameMode
                }
            });
        },
        async getUserStats() {
            return await request<UserStats[]>('/user-stats', {
                method: 'GET'
            });
        }
    };
}
