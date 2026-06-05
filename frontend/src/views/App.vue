<template>
    <Layout>
        <RouterView />
    </Layout>
</template>

<script setup lang="ts">
import Layout from '#/components/layout/Layout.vue';
import { useUserApi } from '#/composables/api/useUserApi';
import { useAuth } from '#/composables/useAuth';
import { useAppStore } from '#/stores/appStore';
import { useStorage } from '@vueuse/core';
import { storeToRefs } from 'pinia';
import { onMounted } from 'vue';

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

async function authUser() {
    checkTokenValidity();
    if (isAuthenticated.value) {
        try {
            user.value = await userApi.getCurrentUser();
        } catch (error) {
            console.error('Failed to fetch user data:', error);
        }
    }
}

onMounted(() => {
    // Clean old local storage keys from users browser
    cleanOldStorageKeys();
    authUser();
});
</script>
