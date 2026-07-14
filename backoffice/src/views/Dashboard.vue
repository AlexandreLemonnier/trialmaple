<template>
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
</template>

<script lang="ts" setup>
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { computed } from 'vue';

const { authState, user } = storeToRefs(useAppStore());


const userInitial = computed(() => {
    return user.value?.username.slice(0, 1).toUpperCase() ?? 'A';
});

function clearCallbackUrl() {
    globalThis.history.replaceState({}, document.title, '/');
}

function logout() {
    authState.value = 'signed-out';
    clearCallbackUrl();
}

</script>
