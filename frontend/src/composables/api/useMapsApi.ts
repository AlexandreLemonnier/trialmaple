import { TrialMap } from "#/types/api/trialMap";
import { useApi } from "../useApi";

export function useMapsApi() {
    const { request } = useApi('/maps');

    return {
        async getMaps() {
            return await request<TrialMap[]>('', {
                method: 'GET'
            });
        }
    }
}
