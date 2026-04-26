import type { Answer } from '#/types/api/answer';
import type { Guess } from '#/types/api/guess';

export type GameData = {
    history: Record<string, Guess>;
    dailyMapUuid: string;
    answer: Answer | null;
};
