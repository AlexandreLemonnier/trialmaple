import { BACKOFFICE_AUTH_TOKEN_STORAGE_KEY } from '#/composables/api/useAdminApi';
import type { User } from '#/types/api/user';
import type { AuthState } from '#/types/AuthState';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAppStore = defineStore('app', () => {
    const user = ref<User | null>(null);
    const authState = ref<AuthState>('checking');
    const error = ref<string | null>(null);

    function logout() {
        localStorage.removeItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY);
        user.value = null;
        error.value = null;
    }

    return { user, authState, error, logout };
});
