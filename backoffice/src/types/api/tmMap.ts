import type { DifficultyCategory } from '#/types/api/difficultyCategory';
import type { TmUser } from '#/types/api/tmUser';

export type TmMap = {
    uuid: string,
    tmxId: number,
    active: boolean,
    name: string,
    displayName: string,
    authors: string[],
    checkpointCount: number,
    points: number,
    difficulty: DifficultyCategory,
    wrTime: string,
    wrYear: number,
    wrHolder: TmUser,
    finisherCount: number,
    releaseYear: number,
    classic: boolean
};

export type CreateTmMap = {
    active: boolean,
    name: string,
    authors: string[],
    checkpointCount: number,
    points: number,
    difficulty: DifficultyCategory,
    wrTime: string,
    wrHolder: TmUser
    finisherCount: number,
    releaseYear: number
};
