import type { DifficultyCategory } from '#/types/api/difficultyCategory';
import type { CreateTmMap, TmMap } from '#/types/api/tmmap/tmMap';

export type TmnfTrialMap = TmMap & {
    difficulty: DifficultyCategory
};

export type CreateTmnfTrialMap = CreateTmMap & {
    difficulty: DifficultyCategory
};
