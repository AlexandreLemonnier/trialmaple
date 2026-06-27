import type { Guess } from '#/types/api/guess';

export type HintInformation = {
    label: string;
    tooltip?: string;
    guessProp: keyof Guess;
};
