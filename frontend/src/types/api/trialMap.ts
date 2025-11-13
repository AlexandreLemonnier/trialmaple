export interface TrialMap {
    name: string;
    authors: string[];
    nbCheckpoints: number;
    difficulty: string;
    points: number;
    nbFinishers: number;
    worldRecord: string | null;
}