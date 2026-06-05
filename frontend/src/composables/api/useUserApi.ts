import { useApi } from '#/composables/useApi';
import type { User } from '#/types/api/user';

export function useUserApi() {
    const { request } = useApi('/users');

    return {
        async getCurrentUser() {
            return await request<User>('/me', {
                method: 'GET'
            });
        }
    };
}
