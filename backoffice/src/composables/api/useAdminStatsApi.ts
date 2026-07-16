import { useAdminApi } from '#/composables/api/useAdminApi';
import type { UserStats } from '#/types/api/userStats';

export function useAdminStatsApi() {
    const { adminRequest } = useAdminApi('/user-stats');

    return {
        async getUserStats(discordId: string) {
            return await adminRequest<UserStats[]>(`/${discordId}`, {
                method: 'GET'
            });
        }
    };
}
