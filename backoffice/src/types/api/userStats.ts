import type { GameMode } from '#/types/api/gameMode';
import type { TmCategory } from '#/types/tmCategory';
import type { TmGame } from '#/types/tmGame';

export type UserStats = {
    averageTries: number;
    winsCount: number;
    dailyMapsCount: number;
    gameMode: GameMode;
    tmGame: TmGame;
    tmCategory: TmCategory;
};
