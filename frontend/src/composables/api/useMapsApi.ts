import { useApi } from '#/composables/useApi';

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMapNames(finished: boolean) {
            return await request<string[]>('/list', {
                method: 'GET',
                query: {
                    finished
                }
            });
        }
    };
}
