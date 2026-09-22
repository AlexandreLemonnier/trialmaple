import { BACKOFFICE_AUTH_TOKEN_STORAGE_KEY } from '#/composables/api/useAdminApi';
import { useApi } from '#/composables/useApi';
import type { LoginResponse } from '#/types/api/loginResponse';
import type { User } from '#/types/api/user';

export function useBackofficeAuthApi() {
    const { request } = useApi('/auth/backoffice');

    return {
        async loginWithDiscord(code: string, redirectUri: string) {
            return await request<LoginResponse>('/discord', {
                method: 'POST',
                body: {
                    code,
                    redirectUri
                },
                // A stale session must not prevent a new Discord login.
                authTokenStorageKey: null
            });
        },

        async getCurrentUser() {
            return await request<User>('/me', {
                authTokenStorageKey: BACKOFFICE_AUTH_TOKEN_STORAGE_KEY
            });
        }
    };
}
