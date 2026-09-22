<template>
    <Layout>
        <GuestLoginModal v-model="isGuestLoginOpen" />
        <RouterView />
    </Layout>
</template>

<script setup lang="ts">
import Layout from '#/components/layout/Layout.vue';
import { RequestError } from '#/classes/RequestError';
import GuestLoginModal from '#/components/modal/GuestLoginModal.vue';
import { useUserApi } from '#/composables/api/useUserApi';
import { useAuth } from '#/composables/useAuth';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const { user } = storeToRefs(useAppStore());
const { AUTH_TOKEN_INVALID_EVENT, checkTokenValidity, clearAuthToken, isAuthenticated } = useAuth();
const userApi = useUserApi();
const isGuestLoginOpen = ref(false);
const router = useRouter();

async function authUser() {
    checkTokenValidity();
    if (isAuthenticated.value) {
        try {
            user.value = await userApi.getCurrentUser();
        } catch (error) {
            console.error('Failed to fetch user data:', error);
            if (error instanceof RequestError && error.statusCode === 401) {
                clearAuthToken();
                showGuestLoginModal();
            }
        }
    } else {
        showGuestLoginModal();
    }
}

async function showGuestLoginModal() {
    user.value = null;
    await router.isReady();
    if (router.currentRoute.value.name !== Route.AUTH_CALLBACK) {
        isGuestLoginOpen.value = true;
    }
}

function onAuthTokenInvalid(event: Event) {
    const { authTokenStorageKey } = (event as CustomEvent<{ authTokenStorageKey: string }>).detail;
    if (authTokenStorageKey === 'auth_token') {
        clearAuthToken();
        showGuestLoginModal();
    }
}

onMounted(async () => {
    globalThis.addEventListener(AUTH_TOKEN_INVALID_EVENT, onAuthTokenInvalid);
    await authUser();
});

onUnmounted(() => {
    globalThis.removeEventListener(AUTH_TOKEN_INVALID_EVENT, onAuthTokenInvalid);
});
</script>
