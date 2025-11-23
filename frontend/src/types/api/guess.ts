import type { DeltaHint } from '#/types/api/deltaHint';

export type HintPair<K, V> = {
    value: K;
    hint: V;
};

export type Guess = {
    success: boolean;
    difficulty: HintPair<string, boolean>;
    points: HintPair<number, DeltaHint>;
    checkpoints: HintPair<number, DeltaHint>;
    nbFinishers: HintPair<number, DeltaHint>;
    worldRecord: HintPair<string, DeltaHint>;
    authors: HintPair<string, boolean>[];
};
