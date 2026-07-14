<template>
    <Layout>
        <GuestLoginModal v-model="isGuestLoginOpen" />
        <RouterView />
    </Layout>
</template>

<script setup lang="ts">
import Layout from '#/components/layout/Layout.vue';
import GuestLoginModal from '#/components/modal/GuestLoginModal.vue';
import { useUserApi } from '#/composables/api/useUserApi';
import { useAuth } from '#/composables/useAuth';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const { user } = storeToRefs(useAppStore());
const { checkTokenValidity, isAuthenticated } = useAuth();
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
        }
    } else {
        await router.isReady();
        if (router.currentRoute.value.name !== Route.AUTH_CALLBACK) {
            isGuestLoginOpen.value = true;
        }
    }
}

onMounted(() => {
    authUser();
});
</script>
