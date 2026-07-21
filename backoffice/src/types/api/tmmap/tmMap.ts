import type { TmUser } from '#/types/api/tmUser';

export type TmMap = {
    uuid: string,
    // tmxId: number,
    active: boolean,
    name: string,
    // displayName: string,
    authors: string[],
    checkpointCount: number,
    points: number,
    wrTime: string,
    // wrYear: number,
    wrHolder: TmUser,
    finisherCount: number,
    releaseYear: number,
    // classic: boolean
};

export type CreateTmMap = {
    active: boolean,
    name: string,
    authors: string[],
    checkpointCount: number,
    points: number,
    wrTime: string,
    wrHolder: TmUser
    finisherCount: number,
    releaseYear: number
};
