import tm2Icon from '#/assets/tm2.png';
import tm2020Icon from '#/assets/tm2020.jpg';
import tmnfIcon from '#/assets/tmnf.jpg';
import { Route } from '#/router/Route';
import type { IconName } from '#/types/IconName';

export const TM_GAMES = ['TMNF', 'TM2', 'TM2020'] as const;

export type TmGame = typeof TM_GAMES[number];

export const TM_CATEGORIES = ['Trial', 'RPG'] as const;

export type TmCategory = typeof TM_CATEGORIES[number];

export type Game = {
    title: TmGame;
    icon: string;
    colorGradient: {
        start: string;
        end: string;
    };
    categories: {
        title: TmCategory;
        gameModes: {
            title: string;
            icon: IconName;
            routeName: Route;
        }[]
    }[]
};

export const games: Game[] = [
    {
        title: 'TM2020',
        icon: tm2020Icon,
        colorGradient: {
            start: '#4dc3e7',
            end: '#5abc89'
        },
        categories: [
            {
                title: 'Trial',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TM2020_TRIAL_CLASSIC_MODE
                    }
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TM2020_RPG_CLASSIC_MODE
                    },
                    {
                        title: 'Geoguessr',
                        icon: 'image',
                        routeName: Route.TM2020_RPG_GEOGUESSR_MODE
                    }
                ]
            }
        ]
    },
    {
        title: 'TM2',
        icon: tm2Icon,
        colorGradient: {
            start: '#155696',
            end: '#55a4df'
        },
        categories: [
            {
                title: 'Trial',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TM2_TRIAL_CLASSIC_MODE
                    }
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TM2_RPG_CLASSIC_MODE
                    }
                ]
            }
        ]
    },
    {
        title: 'TMNF',
        icon: tmnfIcon,
        colorGradient: {
            start: '#316a19',
            end: '#5fb900'
        },
        categories: [
            {
                title: 'Trial',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TMNF_TRIAL_CLASSIC_MODE
                    },
                    {
                        title: 'Geoguessr',
                        icon: 'image',
                        routeName: Route.TMNF_TRIAL_GEOGUESSR_MODE
                    },
                    {
                        title: 'Blurred',
                        icon: 'focus',
                        routeName: Route.TMNF_TRIAL_BLUR_MODE
                    },
                    {
                        title: 'Zoomed',
                        icon: 'search',
                        routeName: Route.TMNF_TRIAL_ZOOM_MODE
                    }
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    {
                        title: 'Classic',
                        icon: 'triangle',
                        routeName: Route.TMNF_RPG_CLASSIC_MODE
                    }
                ]
            }
        ]
    }
];
