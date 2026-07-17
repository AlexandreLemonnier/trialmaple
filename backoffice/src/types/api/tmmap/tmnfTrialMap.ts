import type { DifficultyCategory } from '#/types/api/difficultyCategory';
import type { TmMap } from '#/types/api/tmmap/tmMap';

export type TmnfTrialMap = TmMap & {
    difficulty: DifficultyCategory
};
