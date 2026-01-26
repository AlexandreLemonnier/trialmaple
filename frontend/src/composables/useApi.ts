import { RequestError } from '#/classes/RequestError';
import { useEnv } from '#/composables/useEnv';

function objectToURLSearchParams(obj: Record<string, string | number | boolean | string[] | undefined>) {
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

    return {
        async request<T>(url: string, options: Omit<RequestInit, 'body'> & { body?: Record<string, unknown> | unknown[]; query?: Record<string, string | number | boolean | string[] | undefined> } = {}): Promise<T> {
            try {
                const { query, body, ...baseOptions } = options;
                const path = new URL(globalThis.location.origin + env.PROXIED_API_URL_PREFIX + routePrefix + url);

                if (query) {
                    path.search = objectToURLSearchParams(query).toString();
                }

                const response = await fetch(path.href, {
                    body: JSON.stringify(body),
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    ...baseOptions
                });

                const dataText = await response.text();

                if (!response.ok) {
                    throw new RequestError(
                        dataText ? JSON.parse(dataText)?.message ?? 'unknown' : 'unknown',
                        response.status
                    );
                }

                if (!dataText) return null as T;

                const isJson = response.headers
                    .get('content-type')
                    ?.includes('application/json');

                if (!isJson) {
                    return dataText as T;
                }

                return JSON.parse(dataText) as T;
            } catch (error) {
                if (error instanceof RequestError) {
                    throw error;
                }
                throw new RequestError('unknown', 500);
            }
        }
    };
}
