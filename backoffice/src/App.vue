<template>
    <Layout>
        <RouterView />
    </Layout>
</template>

<script setup lang="ts">
import Layout from '#/components/layout/Layout.vue';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { watch } from 'vue';
import { useRouter } from 'vue-router';

const appStore = useAppStore();
const { authState } = storeToRefs(appStore);
const { logout } = appStore;

const router = useRouter();

watch(authState, () => {
    if (authState.value === 'signed-out') {
        logout();
        router.push({ name: Route.LOGIN });
    } else if (authState.value === 'signed-in') {
        router.push({ name: Route.DASHBOARD });
    }
});

router.beforeEach((to, _, next) => {
    if (authState.value !== 'signed-in' && to.name !== Route.LOGIN) {
        next({ name: Route.LOGIN });
    } else {
        next();
    }
});

</script>
