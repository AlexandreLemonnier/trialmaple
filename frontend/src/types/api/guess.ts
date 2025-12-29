import type { DeltaHint } from '#/types/api/deltaHint';

export type HintPair<K, V> = {
    value: K;
    hint: V;
};

export type Guess = {
    isValidDay: boolean;
    success: boolean;
    difficulty: HintPair<string, boolean>;
    points: HintPair<number, DeltaHint>;
    checkpoints: HintPair<number, DeltaHint>;
    finisherCount: HintPair<number, DeltaHint>;
    wrTime: HintPair<string, DeltaHint>;
    authors: HintPair<string, boolean>[];
    releaseYear: HintPair<number, DeltaHint>;
};
