<template>
    <section class="workspace">
        <header class="workspace-header">
            <div>
                <p class="eyebrow">Admin session</p>
                <H1>Dashboard</H1>
            </div>
        </header>

        <div class="overview-grid">
            <article class="metric-card">
                <span>Role</span>
                <strong>{{ adminUser?.userType }}</strong>
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

        <section v-if="adminUser" class="details-panel">
            <div class="user-chip">
                <span class="avatar-fallback">{{ userInitial }}</span>
                <div>
                    <strong>{{ adminUser.username }}</strong>
                    <span>{{ adminUser.discordId }}</span>
                </div>
            </div>

            <dl class="details-list">
                <div>
                    <dt>Discord ID</dt>
                    <dd>{{ adminUser.discordId }}</dd>
                </div>
                <div>
                    <dt>Username</dt>
                    <dd>{{ adminUser.username }}</dd>
                </div>
                <div>
                    <dt>Access</dt>
                    <dd>{{ adminUser.userType }}</dd>
                </div>
            </dl>
        </section>
    </section>
</template>

<script lang="ts" setup>
import H1 from '#/components/H1.vue';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { computed } from 'vue';

const { adminUser } = storeToRefs(useAppStore());

const userInitial = computed(() => {
    return adminUser.value?.username.slice(0, 1).toUpperCase() ?? 'A';
});

</script>
