import type { ClassicGameMode } from '#/types/api/gameMode';
import { ExternalMapList } from '#/types/ExternalMapList';

const MAP_LISTS: Record<ClassicGameMode, {
    url: ExternalMapList,
    name: string
}> = {
    CLASSIC_TMNF_TRIAL: {
        url: ExternalMapList.TMNF_TRIAL_LIST,
        name: 'TMNF Hardest Trials'
    },
    CLASSIC_TMNF_RPG: {
        url: ExternalMapList.TMNF_RPG_LIST,
        name: 'TMNF Classic RPG'
    },
    CLASSIC_TM2_TRIAL: {
        url: ExternalMapList.TM2_TRIAL,
        name: 'TM2 Trial'
    },
    CLASSIC_TM2_RPG: {
        url: ExternalMapList.TM2_RPG_PVM,
        name: 'TM2 RPG PVM'
    },
    CLASSIC_TM2020_TRIAL: {
        url: ExternalMapList.TM2020_TRIAL_CLASSIC,
        name: 'TM2020 Classic Trial'
    },
    CLASSIC_TM2020_RPG: {
        url: ExternalMapList.TM2020_RPG,
        name: 'TM2020 RPG'
    }
};

export function getMapList(gameMode: ClassicGameMode) {
    return MAP_LISTS[gameMode];
}
