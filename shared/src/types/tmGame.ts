export const TM_GAMES = ['TMNF', 'TM2', 'TM2020'] as const;

export type TmGame = typeof TM_GAMES[number];