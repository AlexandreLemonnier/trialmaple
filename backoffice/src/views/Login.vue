<template>
    <div class="min-h-screen flex items-center justify-center p-6 box-border">
        <div class="w-full max-w-105 box-border rounded-lg border border-app-border bg-login-background p-7 shadow-[0_24px_70px_rgb(23_33_27/10%)]">
            <Brand />

            <div class="mt-7 px-3.5 py-3 rounded-lg bg-app-background text-brand-primary font-bold"
                 :class="authState === 'denied' && 'bg-error-background text-text-error'"
                 aria-live="polite">
                {{ statusLabel }}
            </div>

            <p v-if="error" class="mt-4 leading-[1.45]">{{ error }}</p>

            <Button class="w-full bg-discord text-primary-white font-bold py-2 mt-5"
                    :label="isBusy ? 'Connecting...' : 'Sign in with Discord'"
                    icon-name="discord"
                    :disabled="isBusy"
                    @click="startDiscordLogin" />
        </div>
    </div>
</template>

<script lang="ts" setup>
import { RequestError } from '#/classes/RequestError';
import Button from '#/components/Button.vue';
import Brand from '#/components/layout/Brand.vue';
import { BACKOFFICE_AUTH_TOKEN_STORAGE_KEY } from '#/composables/api/useAdminApi';
import { useBackofficeAuthApi } from '#/composables/api/useBackofficeAuthApi';
import { useEnv } from '#/composables/useEnv';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const { DISCORD_CLIENT_ID } = useEnv();
const { authState, error, user } = storeToRefs(useAppStore());
const route = useRoute();
const router = useRouter();

const authApi = useBackofficeAuthApi();

const isBusy = computed(() => authState.value === 'checking' || authState.value === 'authenticating');

const statusLabel = computed(() => {
    switch (authState.value) {
        case 'checking':
            return 'Checking session...';
        case 'authenticating':
            return 'Authenticating...';
        case 'denied':
            return 'Access denied.';
        default:
            return 'Sign in required.';
    }
});

function getCallbackUrl() {
    return new URL(Route.LOGIN, globalThis.location.origin).href;
}

function clearCallbackUrl() {
    globalThis.history.replaceState({}, document.title, '/');
}

function startDiscordLogin() {
    if (isBusy.value) return;

    const params = new URLSearchParams({
        client_id: DISCORD_CLIENT_ID,
        redirect_uri: getCallbackUrl(),
        response_type: 'code',
        scope: 'identify'
    });

    globalThis.location.assign(`https://discord.com/api/oauth2/authorize?${params.toString()}`);
}

function getAuthErrorMessage(err: unknown) {
    if (err instanceof RequestError) {
        if (err.statusCode === 401) return 'Session expired. Please sign in again.';
        if (err.statusCode === 403) return 'This account is not allowed to access the backoffice.';
        return err.errorCode;
    }

    return 'Authentication failed.';
}

async function finishDiscordLogin(code: string) {
    authState.value = 'authenticating';
    error.value = null;

    try {
        const response = await authApi.loginWithDiscord(code, getCallbackUrl());

        if (response.user.userType !== 'ADMIN') {
            throw new RequestError('BACKOFFICE_ACCESS_DENIED', 403);
        }

        localStorage.setItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY, response.token);
        user.value = response.user;
        authState.value = 'signed-in';

        router.push({ name: Route.DASHBOARD });

        clearCallbackUrl();
    } catch (err) {
        localStorage.removeItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY);
        user.value = null;
        authState.value = 'denied';
        error.value = getAuthErrorMessage(err);
        clearCallbackUrl();
    }
}

async function restoreSession() {
    const token = localStorage.getItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY);

    if (!token) {
        authState.value = 'signed-out';
        return;
    }

    authState.value = 'checking';
    error.value = null;

    try {
        const currentUser = await authApi.getCurrentUser();

        if (currentUser.userType !== 'ADMIN') {
            throw new RequestError('BACKOFFICE_ACCESS_DENIED', 403);
        }

        user.value = currentUser;
        authState.value = 'signed-in';
    } catch (err) {
        localStorage.removeItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY);
        user.value = null;
        authState.value = err instanceof RequestError && err.statusCode === 401 ? 'signed-out' : 'denied';
        error.value = getAuthErrorMessage(err);
    }
}

onMounted(async () => {
    const code = route.query.code as string;

    if (code) {
        await finishDiscordLogin(code);
    }
});
</script>
