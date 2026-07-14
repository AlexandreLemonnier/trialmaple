import type { User } from '#/types/api/user';
import { useAdminApi } from './useAdminApi';

export function useAdminUserApi() {
    const { adminRequest } = useAdminApi('/users');

    return {
        async getAllUsers() {
            return await adminRequest<User[]>('', {
                method: 'GET'
            });
        }
    };
}
