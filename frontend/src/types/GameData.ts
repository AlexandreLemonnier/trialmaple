import type { Guess } from '#/types/api/guess';

export type GameData = {
    history: Record<string, Guess>;
    dailyMapUuid: string;
    gaveUp: boolean;
};
