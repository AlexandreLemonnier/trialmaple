import { DeltaHint } from "#/types/api/deltaHint";

export interface HintPair<K, V> {
    value: K;
    hint: V;
}

export interface Guess {
    success: boolean;
    difficulty: HintPair<string, boolean>;
    points: HintPair<number, DeltaHint>;
    checkpoints: HintPair<number, DeltaHint>;
    nbFinishers: HintPair<number, DeltaHint>;
    worldRecord: HintPair<string | null, DeltaHint>;
    authors: HintPair<string, boolean>[];
}