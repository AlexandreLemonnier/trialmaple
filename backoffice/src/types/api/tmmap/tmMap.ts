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
