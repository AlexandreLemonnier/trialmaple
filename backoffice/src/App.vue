<template>
    <main class="backoffice-shell">
        <section v-if="authState !== 'signed-in'" class="auth-screen">
            <div class="auth-panel">
                <div class="brand-row">
                    <span class="brand-mark">TM</span>
                    <div>
                        <p class="eyebrow">TrialMaple</p>
                        <h1>Backoffice</h1>
                    </div>
                </div>

                <div class="status-line" :data-state="authState" aria-live="polite">
                    {{ statusLabel }}
                </div>

                <p v-if="error" class="error-message">{{ error }}</p>

                <button class="primary-action" type="button" :disabled="isBusy" @click="startDiscordLogin">
                    <span class="button-icon" aria-hidden="true">D</span>
                    <span>{{ isBusy ? 'Connecting...' : 'Sign in with Discord' }}</span>
                </button>
            </div>
        </section>

        <section v-else class="admin-layout">
            <aside class="sidebar">
                <div class="brand-row compact">
                    <span class="brand-mark">TM</span>
                    <div>
                        <p class="eyebrow">TrialMaple</p>
                        <strong>Backoffice</strong>
                    </div>
                </div>

                <nav class="nav-list" aria-label="Backoffice navigation">
                    <a class="nav-item active" href="/">Dashboard</a>
                </nav>
            </aside>

            <section class="workspace">
                <header class="workspace-header">
                    <div>
                        <p class="eyebrow">Admin session</p>
                        <h1>Dashboard</h1>
                    </div>

                    <button class="secondary-action" type="button" @click="logout">Sign out</button>
                </header>

                <div class="overview-grid">
                    <article class="metric-card">
                        <span>Role</span>
                        <strong>{{ user?.userType }}</strong>
                    </article>
                    <article class="metric-card">
                        <span>API</span>
                        <strong>Connected</strong>
                    </article>
                    <article class="metric-card">
                        <span>Session</span>
                        <strong>Active</strong>
                    </article>
                </div>

                <section v-if="user" class="details-panel">
                    <div class="user-chip">
                        <span class="avatar-fallback">{{ userInitial }}</span>
                        <div>
                            <strong>{{ user.username }}</strong>
                            <span>{{ user.discordId }}</span>
                        </div>
                    </div>

                    <dl class="details-list">
                        <div>
                            <dt>Discord ID</dt>
                            <dd>{{ user.discordId }}</dd>
                        </div>
                        <div>
                            <dt>Username</dt>
                            <dd>{{ user.username }}</dd>
                        </div>
                        <div>
                            <dt>Access</dt>
                            <dd>{{ user.userType }}</dd>
                        </div>
                    </dl>
                </section>
            </section>
        </section>
    </main>
</template>

<script setup lang="ts">
import { RequestError } from '#/classes/RequestError';
import {
    BACKOFFICE_AUTH_TOKEN_STORAGE_KEY,
    useBackofficeAuthApi
} from '#/composables/api/useBackofficeAuthApi';
import type { User } from '#/types/api/user';
import { computed, onMounted, ref } from 'vue';

type AuthState = 'checking' | 'authenticating' | 'signed-out' | 'signed-in' | 'denied';

const CALLBACK_PATH = '/auth/callback';
const DISCORD_CLIENT_ID = import.meta.env.VITE_DISCORD_CLIENT_ID || '1511852531100160010';

const authApi = useBackofficeAuthApi();
const authState = ref<AuthState>('checking');
const error = ref<string | null>(null);
const user = ref<User | null>(null);

const isBusy = computed(() => authState.value === 'checking' || authState.value === 'authenticating');

const statusLabel = computed(() => {
    switch (authState.value) {
        case 'checking':
            return 'Checking session';
        case 'authenticating':
            return 'Authenticating';
        case 'denied':
            return 'Access denied';
        default:
            return 'Sign in required';
    }
});

const userInitial = computed(() => {
    return user.value?.username.slice(0, 1).toUpperCase() ?? 'A';
});

function getCallbackUrl() {
    return new URL(CALLBACK_PATH, globalThis.location.origin).href;
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

function logout() {
    localStorage.removeItem(BACKOFFICE_AUTH_TOKEN_STORAGE_KEY);
    user.value = null;
    error.value = null;
    authState.value = 'signed-out';
    clearCallbackUrl();
}

onMounted(async () => {
    const params = new URLSearchParams(globalThis.location.search);
    const code = params.get('code');

    if (code) {
        await finishDiscordLogin(code);
        return;
    }

    await restoreSession();
});
</script>
