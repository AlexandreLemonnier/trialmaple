import { useAdminApi } from '#/composables/api/useAdminApi';
import type { User } from '#/types/api/user';

export function useAdminUserApi() {
    const { adminRequest } = useAdminApi('/users');

    return {
        async getAllUsers() {
            return await adminRequest<User[]>('', {
                method: 'GET'
            });
        },
        async getUserById(discordId: string) {
            return await adminRequest<User>(`/${discordId}`, {
                method: 'GET'
            });
        }
    };
}
