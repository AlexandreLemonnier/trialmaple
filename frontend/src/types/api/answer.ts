import type { Guess } from '#/types/api/guess';

export type Answer = {
    mapName: string;
    mapUuid: string | null;
    answerDetails: Guess | null;
};
