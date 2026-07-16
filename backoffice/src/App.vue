<template>
    <Layout>
        <RouterView />
    </Layout>
</template>

<script setup lang="ts">
import Layout from '#/components/layout/Layout.vue';
import { BACKOFFICE_AUTH_TOKEN_STORAGE_KEY } from '#/composables/api/useAdminApi';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { watch } from 'vue';
import type { RouteLocationNormalizedGeneric } from 'vue-router';
import { useRouter } from 'vue-router';
import { useBackofficeAuthApi } from './composables/api/useBackofficeAuthApi';

const appStore = useAppStore();
const { authState, adminUser } = storeToRefs(appStore);
const { logout } = appStore;

const router = useRouter();
const authApi = useBackofficeAuthApi();

let hasInitializedAuth = false;

watch(authState, () => {
    if (authState.value === 'signed-out') {
        logout();
        router.push({ name: Route.LOGIN });
    }
});

async function initializeAuth(to: RouteLocationNormalizedGeneric) {
    hasInitializedAuth = true;
    const token = localStorage.getItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY);
    const hasDiscordCode = !!to.query.code;

    if (token && !hasDiscordCode) {
        authState.value = 'checking';
        try {
            const currentUser = await authApi.getCurrentUser();
            if (currentUser.userType === 'ADMIN') {
                adminUser.value = currentUser;
                authState.value = 'signed-in';
            } else {
                authState.value = 'denied';
            }
        } catch (err) {
            console.error('Error restoring session:', err);
            localStorage.removeItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY);
            adminUser.value = null;
            authState.value = 'signed-out';
        }
    } else if (!hasDiscordCode) {
        authState.value = 'signed-out';
    }
}

router.beforeEach(async (to, _, next) => {
    if (!hasInitializedAuth) {
        await initializeAuth(to);
    }

    // Secure routes
    if (authState.value !== 'signed-in' && to.name !== Route.LOGIN) {
        next({ name: Route.LOGIN });
    } else if (authState.value === 'signed-in' && to.name === Route.LOGIN) {
        next({ name: Route.USERS });
    } else {
        next();
    }
});

</script>
