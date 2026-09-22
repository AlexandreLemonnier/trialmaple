import { useApi } from '#/composables/useApi';
import type { LoginResponse } from '#/types/api/loginResponse';

export function useAuthApi() {
    const { request } = useApi('/auth');

    return {
        async loginWithDiscord(code: string) {
            return await request<LoginResponse>('/discord', {
                method: 'POST',
                body: {
                    code
                },
                // A stale session must not prevent a new Discord login.
                authTokenStorageKey: null
            });
        }
    };
}
