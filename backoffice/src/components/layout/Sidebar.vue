<template>
    <aside class="md:sticky md:top-0 md:h-screen box-border flex flex-col border-b border-app-border bg-sidebar-background px-4.5 py-6 md:border-b-0 md:border-r">
        <Brand compact />
        <div class="mt-8 flex flex-1 flex-col justify-between gap-4">
            <nav class="flex flex-col gap-2" aria-label="Backoffice navigation">
                <div v-for="item in navItems" :key="item.label">
                    <RouterLink v-if="'route' in item"
                                :to="item.route"
                                class="flex items-center gap-2 p-3 rounded-lg text-sidebar-nav-item font-extrabold"
                                :class="isActive(item.route) && 'bg-app-background text-brand-primary'">
                        <Icon :name="item.icon" size="md" />
                        {{ item.label }}
                    </RouterLink>
                    <div v-else>
                        <div class="flex items-center gap-2 p-3 rounded-lg text-sidebar-nav-item font-extrabold">
                            <Icon :name="item.icon" size="md" />
                            {{ item.label }}
                        </div>

                        <div class="ml-14 flex flex-col gap-1">
                            <RouterLink v-for="subItem in item.subItems"
                                        :key="subItem.route"
                                        :to="subItem.route"
                                        class="p-2 rounded-lg text-sidebar-nav-item font-bold"
                                        :class="isActive(subItem.route) && 'bg-app-background text-brand-primary'">
                                {{ subItem.label }}
                            </RouterLink>
                        </div>
                    </div>
                </div>
            </nav>
            <Button class="w-full" label="Sign out" @click="logout" />
        </div>
    </aside>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import Icon from '#/components/Icon.vue';
import Brand from '#/components/layout/Brand.vue';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import type { IconName } from '#/types/IconName';
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';

const route = useRoute();
const { authState } = storeToRefs(useAppStore());

type NavItem =
  {
      label: string;
      icon: IconName;
  } & (
    {
        route: Route;
    } | {
        subItems: {
            label: string;
            route: Route;
        }[];
    }
  );

const navItems: NavItem[] = [
    {
        label: 'Users',
        icon: 'user',
        route: Route.USERS
    },
    {
        label: 'TM Maps',
        icon: 'track',
        subItems: [
            {
                label: 'TMNF Trial',
                route: Route.TMNF_TRIAL_MAPS
            }
        ]
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
