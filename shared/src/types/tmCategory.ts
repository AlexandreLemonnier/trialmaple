export const TM_CATEGORIES = ['Trial', 'RPG'] as const;

export type TmCategory = typeof TM_CATEGORIES[number];