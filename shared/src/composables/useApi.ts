import { RequestError } from '../classes/RequestError.js';
import { useEnv } from './useEnv.js';

type QueryValue = string | number | boolean | string[] | undefined;

type RequestOptions = Omit<RequestInit, 'body'> & {
    body?: Record<string, unknown> | unknown[];
    query?: Record<string, QueryValue>;
    authTokenStorageKey?: string;
};

async function waitMinimumTime(start: number) {
    const requestMinimumTime = 500;
    const requestDuration = performance.now() - start;

    if (requestDuration < requestMinimumTime) {
        await new Promise((resolve) => {
            globalThis.setTimeout(resolve, requestMinimumTime - requestDuration);
        });
    }
}

function objectToURLSearchParams(obj: Record<string, QueryValue>) {
    const entries: [string, string][] = [];

    for (const key in obj) {
        const value = obj[key];

        if (value === undefined) continue;

        if (Array.isArray(value)) {
            value.forEach((v) => {
                entries.push([key, String(v)]);
            });
        } else {
            entries.push([key, String(value)]);
        }
    }

    return new URLSearchParams(entries);
}

export function useApi(routePrefix: string) {
    const env = useEnv();

    async function request<T>(url: string, options: RequestOptions = {}): Promise<T> {
        const start = performance.now();

        try {
            const { query, body, authTokenStorageKey = 'auth_token', ...baseOptions } = options;
            const path = new URL(globalThis.location.origin + env.PROXIED_API_URL_PREFIX + routePrefix + url);

            if (query) {
                path.search = objectToURLSearchParams(query).toString();
            }

            const token = localStorage.getItem(authTokenStorageKey);

            const response = await fetch(path.href, {
                body: body === undefined ? undefined : JSON.stringify(body),
                headers: {
                    'Content-Type': 'application/json',
                    ...(token ? { Authorization: `Bearer ${token}` } : {})
                },
                ...baseOptions
            });

            const dataText = await response.text();

            if (!response.ok) {
                throw new RequestError(
                    JSON.parse(dataText)?.message ?? 'unknown',
                    response.status
                );
            }

            await waitMinimumTime(start);

            if (!dataText) return null as T;

            const isJson = response.headers
                .get('content-type')
                ?.includes('application/json');

            if (!isJson) {
                return dataText as T;
            }

            return JSON.parse(dataText) as T;
        } catch (error) {
            await waitMinimumTime(start);

            if (error instanceof RequestError) {
                throw error;
            }

            throw new RequestError('unknown', 500);
        }
    }

    return { request };
}
