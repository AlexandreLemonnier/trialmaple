<template>
    <aside class="box-border flex flex-col border-b border-app-border bg-sidebar-background px-4.5 py-6 md:border-b-0 md:border-r">
        <Brand compact />
        <div class="mt-8 flex flex-1 flex-col justify-between">
            <nav class="flex flex-col gap-2" aria-label="Backoffice navigation">
                <RouterLink v-for="item in navItems"
                            :key="item.route"
                            :to="item.route"
                            class="p-3 rounded-lg text-sidebar-nav-item font-extrabold"
                            :class="isActive(item.route) && 'bg-app-background text-brand-primary'">
                    {{ item.label }}
                </RouterLink>
            </nav>
            <Button class="w-full" label="Sign out" @click="logout" />
        </div>
    </aside>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import Brand from '#/components/layout/Brand.vue';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';

const route = useRoute();
const { authState } = storeToRefs(useAppStore());

const navItems: { label: string; route: Route }[] = [
    {
        label: 'Dashboard',
        route: Route.DASHBOARD
    }
];

function isActive(_route: Route) {
    return _route === route.name;
}

function clearCallbackUrl() {
    globalThis.history.replaceState({}, document.title, '/');
}

function logout() {
    authState.value = 'signed-out';
    clearCallbackUrl();
}

</script>
