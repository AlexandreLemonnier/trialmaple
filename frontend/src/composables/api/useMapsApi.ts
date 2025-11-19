import { useApi } from "#/composables/useApi";
import { TrialMap } from "#/types/api/trialMap";

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMaps() {
            return await request<TrialMap[]>('', {
                method: 'GET'
            });
        },
        async getMapNames(finished: boolean) {
            return await request<string[]>('/list', {
                method: 'GET',
                query: {
                    finished
                }
            });
        }
    }
}
