import type { TmCategory, TmGame } from '#/types/Game';
import type { GameMode } from '#/types/api/gameMode';

export type UserStats = {
    averageTries: number;
    winsCount: number;
    dailyMapsCount: number;
    gameMode: GameMode;
    tmGame: TmGame;
    tmCategory: TmCategory;
};
