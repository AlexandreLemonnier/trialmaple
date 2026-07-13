import { useApi } from '#/composables/useApi';
import type { LoginResponse } from '#/types/api/loginResponse';
import type { User } from '#/types/api/user';

export const BACKOFFICE_AUTH_TOKEN_STORAGE_KEY = 'backoffice_auth_token';

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
                authTokenStorageKey: BACKOFFICE_AUTH_TOKEN_STORAGE_KEY
            });
        },

        async getCurrentUser() {
            return await request<User>('/me', {
                authTokenStorageKey: BACKOFFICE_AUTH_TOKEN_STORAGE_KEY
            });
        }
    };
}
