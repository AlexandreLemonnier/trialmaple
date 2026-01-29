import type { OneShotModalName } from '#/types/OneShotModalName';
import { useStorage } from '@vueuse/core';
import { defineStore } from 'pinia';

const defaultModals: Record<OneShotModalName, boolean> = {
    FAVORITE_PAGE: false
};

export const useModalStore = defineStore('modal', () => {
    const modals = useStorage<Record<OneShotModalName, boolean>>('modals', defaultModals);

    return { modals };
});
