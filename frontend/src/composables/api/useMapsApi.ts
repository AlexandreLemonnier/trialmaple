import { useApi } from '#/composables/useApi';

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMapNames() {
            return await request<string[]>('/list', {
                method: 'GET',
                query: {
                    gameMode: 'CLASSIC_TMNF_TRIAL'
                }
            });
        }
    };
}
