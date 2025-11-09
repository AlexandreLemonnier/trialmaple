import { DailyStats } from "#/types/api/dailyStats";
import { useApi } from "../useApi";

export function useDailyStatsApi() {
    const { request } = useApi('/daily-stats');

    return {
        async getDailyStats() {
            return await request<DailyStats>('', {
                method: 'GET'
            });
        }
    }
}
