import tm2Icon from '#/assets/tm2.png';
import tm2020Icon from '#/assets/tm2020.jpg';
import tmnfIcon from '#/assets/tmnf.jpg';
import { gamesInfo, type GameInfo } from '#/types/GameInfo';

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
        gameModes: GameInfo[]
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
                    gamesInfo['CLASSIC_TM2020_TRIAL']
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    gamesInfo['CLASSIC_TM2020_RPG'],
                    gamesInfo['GEOGUESSR_TM2020_RPG']
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
                    gamesInfo['CLASSIC_TM2_TRIAL']
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    gamesInfo['CLASSIC_TM2_RPG']
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
                    gamesInfo['CLASSIC_TMNF_TRIAL'],
                    gamesInfo['GEOGUESSR_TMNF_TRIAL'],
                    gamesInfo['BLUR_TMNF_TRIAL'],
                    gamesInfo['ZOOM_TMNF_TRIAL']
                ]
            },
            {
                title: 'RPG',
                gameModes: [
                    gamesInfo['CLASSIC_TMNF_RPG']
                ]
            }
        ]
    }
];
