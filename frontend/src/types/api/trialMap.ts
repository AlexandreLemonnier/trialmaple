export type TrialMap = {
    name: string;
    authors: string[];
    nbCheckpoints: number;
    difficulty: string;
    points: number;
    nbFinishers: number;
    acceptedAnswers: string[];
    worldRecord: string | null;
}