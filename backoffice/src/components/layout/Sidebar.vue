<template>
    <aside class="md:sticky md:top-0 md:h-screen box-border flex flex-col border-b border-app-border bg-sidebar-background px-4.5 py-6 md:border-b-0 md:border-r">
        <Brand compact />
        <div class="mt-8 flex flex-1 flex-col justify-between gap-4">
            <nav class="flex flex-col gap-2" aria-label="Backoffice navigation">
                <RouterLink v-for="item in navItems"
                            :key="item.route"
                            :to="item.route"
                            class="p-3 rounded-lg text-sidebar-nav-item font-extrabold"
                            :class="isActive(item.route) && 'bg-app-background text-brand-primary'">
                    <div class="flex gap-2 items-center">
                        <Icon :name="item.icon" size="md" />
                        {{ item.label }}
                    </div>
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
import type { IconName } from '#/types/IconName';
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import Icon from '../Icon.vue';

const route = useRoute();
const { authState } = storeToRefs(useAppStore());

const navItems: {
    label: string;
    icon: IconName;
    route: Route
}[] = [
    {
        label: 'Users',
        icon: 'user',
        route: Route.USERS
    }
];

function isActive(_route: Route) {
    return route.name?.toString().includes(_route);
}

function clearCallbackUrl() {
    globalThis.history.replaceState({}, document.title, '/');
}

function logout() {
    authState.value = 'signed-out';
    clearCallbackUrl();
}

</script>
