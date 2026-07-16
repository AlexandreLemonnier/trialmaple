import { useApi } from '#/composables/useApi';

type QueryValue = string | number | boolean | string[] | undefined;

export type RequestOptions = Omit<RequestInit, 'body'> & {
    body?: Record<string, unknown> | unknown[];
    query?: Record<string, QueryValue>;
    authTokenStorageKey?: string;
};

export const BACKOFFICE_AUTH_TOKEN_STORAGE_KEY = 'backoffice_auth_token';

export function useAdminApi(routePrefix: string) {
    async function adminRequest<T>(url: string, options: RequestOptions = {}): Promise<T> {
        const { request } = useApi(`/admin${routePrefix}`);
        return await request<T>(url, {
            ...options,
            authTokenStorageKey: BACKOFFICE_AUTH_TOKEN_STORAGE_KEY
        });
    }
    return { adminRequest };
}
