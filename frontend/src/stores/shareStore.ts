import type { GameMode } from '#/types/api/gameMode';
import { defineStore } from 'pinia';

export const useShareStore = defineStore('share', () => {
    const formattedShareString: Partial<Record<GameMode, string>> = {};

    function setFormattedShareString(gameMode: GameMode, str: string) {
        formattedShareString[gameMode] = str;
    }

    function getFormattedShareString(gameMode: GameMode) {
        return formattedShareString[gameMode];
    }

    return { setFormattedShareString, getFormattedShareString };
});
