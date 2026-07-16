import { TmCategory } from "../tmCategory";
import { TmGame } from "../tmGame";
import { GameMode } from "./gameMode";

export type UserStats = {
    averageTries: number;
    winsCount: number;
    dailyMapsCount: number;
    gameMode: GameMode;
    tmGame: TmGame;
    tmCategory: TmCategory;
};
