import { DeltaHint } from "#/types/api/deltaHint";

export type Guess = {
    success: boolean;
    correctAuthors: string[];
    correctDifficulty: boolean;
    deltaPoints: DeltaHint;
    deltaWR: DeltaHint;
    deltaFinishers: DeltaHint;
}