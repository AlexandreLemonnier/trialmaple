import { Route } from '#/router/Route';
import type { GameMode } from '#/types/api/gameMode';
import type { IconName } from '#/types/IconName';

export type GameInfo = {
    title: string;
    icon: IconName;
    routeName: Route;
    nextGameMode?: GameMode;
};

export const gamesInfo: Record<GameMode, GameInfo> = {
    /* TM2020 Trial */
    CLASSIC_TM2020_TRIAL: {
        title: 'Classic',
        icon: 'triangle',
        routeName: Route.TM2020_TRIAL_CLASSIC_MODE
    },
    /* TM2020 RPG */
    CLASSIC_TM2020_RPG: {
        title: 'Classic',
        icon: 'triangle',
        routeName: Route.TM2020_RPG_CLASSIC_MODE,
        nextGameMode: 'GEOGUESSR_TM2020_RPG'
    },
    GEOGUESSR_TM2020_RPG: {
        title: 'Geoguessr',
        icon: 'image',
        routeName: Route.TM2020_RPG_GEOGUESSR_MODE,
        nextGameMode: 'CLASSIC_TM2020_RPG'
    },
    /* TM2 Trial */
    CLASSIC_TM2_TRIAL: {
        title: 'Classic',
        icon: 'triangle',
        routeName: Route.TM2_TRIAL_CLASSIC_MODE
    },
    /* TM2 RPG */
    CLASSIC_TM2_RPG: {
        title: 'Classic',
        icon: 'triangle',
        routeName: Route.TM2_RPG_CLASSIC_MODE
    },
    /* TMNF Trial */
    CLASSIC_TMNF_TRIAL: {
        title: 'Classic',
        icon: 'triangle',
        routeName: Route.TMNF_TRIAL_CLASSIC_MODE,
        nextGameMode: 'GEOGUESSR_TMNF_TRIAL'
    },
    GEOGUESSR_TMNF_TRIAL: {
        title: 'Geoguessr',
        icon: 'image',
        routeName: Route.TMNF_TRIAL_GEOGUESSR_MODE,
        nextGameMode: 'BLUR_TMNF_TRIAL'
    },
    BLUR_TMNF_TRIAL: {
        title: 'Blurred',
        icon: 'focus',
        routeName: Route.TMNF_TRIAL_BLUR_MODE,
        nextGameMode: 'ZOOM_TMNF_TRIAL'
    },
    ZOOM_TMNF_TRIAL: {
        title: 'Zoomed',
        icon: 'search',
        routeName: Route.TMNF_TRIAL_ZOOM_MODE,
        nextGameMode: 'CLASSIC_TMNF_TRIAL'
    },
    /* TMNF RPG */
    CLASSIC_TMNF_RPG: {
        title: 'Classic',
        icon: 'triangle',
        routeName: Route.TMNF_RPG_CLASSIC_MODE
    }
};
