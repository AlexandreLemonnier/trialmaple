export const DIFFICULTY_CATEGORIES = ['CHALLENGING', 'EXPERT', 'HARDCORE', 'EXTREME', 'INHUMANE', 'NAMELESS'] as const;
export type DifficultyCategory = typeof DIFFICULTY_CATEGORIES[number];
