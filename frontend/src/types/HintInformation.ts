import type { Guess } from './api/guess';

export type HintInformation = {
    label: string;
    tooltip?: string;
    guessProp: keyof Guess;
};
