import type { TmGame } from '#/types/tmGame';

export type TmUser = {
    login: string,
    displayName: string,
    game: TmGame
};
