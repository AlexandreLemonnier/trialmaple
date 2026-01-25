import type { DeltaHint } from '#/types/api/deltaHint';

export type HintPair<K, V> = {
    value: K;
    hint: V;
};

export type WrHolder = {
    login: string,
    displayName: string
};

export type Guess = {
    isValidDay: boolean;
    success: boolean;
    difficulty: HintPair<string, boolean> | null;
    points: HintPair<number, DeltaHint> | null;
    checkpoints: HintPair<number, DeltaHint> | null;
    finisherCount: HintPair<number, DeltaHint> | null;
    wrTime: HintPair<string, DeltaHint> | null;
    wrHolder: HintPair<WrHolder, boolean> | null;
    wrYear: HintPair<number, DeltaHint> | null;
    authors: HintPair<string, boolean>[];
    releaseYear: HintPair<number, DeltaHint> | null;
    classic: HintPair<boolean, boolean> | null;
};
