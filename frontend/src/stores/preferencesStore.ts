import type { Route } from '#/router/Route';
import { useStorage } from '@vueuse/core';
import { defineStore } from 'pinia';

export const usePreferencesStore = defineStore('preferences', () => {
    const favoritePage = useStorage<Route>('favoritePage', null);

    return { favoritePage };
});
