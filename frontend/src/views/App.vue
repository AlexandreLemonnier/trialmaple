<template>
    <Layout>
        <RouterView />
    </Layout>
    <GuestLoginModal v-model="isGuestLoginOpen" />
</template>

<script setup lang="ts">
import Layout from '#/components/layout/Layout.vue';
import GuestLoginModal from '#/components/modal/GuestLoginModal.vue';
import { useUserApi } from '#/composables/api/useUserApi';
import { useAuth } from '#/composables/useAuth';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { useStorage } from '@vueuse/core';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

function cleanOldStorageKeys() {
    const keysToClean = [
        'favoritePage',
        'tm2020RpgGeoguessrDailyMapUuid',
        'tm2020RpgGeoguessrHistory',
        'tm2020TrialClassicDailyMapUuid',
        'tm2020TrialClassicHistory',
        'tm2RpgClassicDailyMapUuid',
        'tm2RpgClassicHistory',
        'tm2TrialClassicDailyMapUuid',
        'tm2TrialClassicHistory',
        'tmTrialClassicDailyMapUuid',
        'tmnfRpgClassicDailyMapUuid',
        'tmnfRpgClassicHistory',
        'tmnfTrialClassicDailyMapUuid',
        'tmnfTrialClassicHistory'
    ];

    keysToClean.forEach((key) => {
        const storageItem = useStorage(key, null);
        storageItem.value = null;
    });
}

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
    // Clean old local storage keys from users browser
    cleanOldStorageKeys();
    authUser();
});
</script>
