<template>
    <Layout>
        <RouterView />
    </Layout>
</template>

<script setup lang="ts">
import Layout from '#/components/layout/Layout.vue';
import { useStorage } from '@vueuse/core';
import { onMounted } from 'vue';

onMounted(() => {
    // Clean old local storage keys from users browser
    const favoritePage = useStorage('favoritePage', null);
    favoritePage.value = null;
    const tm2020RpgGeoguessrDailyMapUuid = useStorage('tm2020RpgGeoguessrDailyMapUuid', null);
    tm2020RpgGeoguessrDailyMapUuid.value = null;
    const tm2020RpgGeoguessrHistory = useStorage('tm2020RpgGeoguessrHistory', null);
    tm2020RpgGeoguessrHistory.value = null;
    const tm2020TrialClassicDailyMapUuid = useStorage('tm2020TrialClassicDailyMapUuid', null);
    tm2020TrialClassicDailyMapUuid.value = null;
    const tm2020TrialClassicHistory = useStorage('tm2020TrialClassicHistory', null);
    tm2020TrialClassicHistory.value = null;
    const tm2RpgClassicDailyMapUuid = useStorage('tm2RpgClassicDailyMapUuid', null);
    tm2RpgClassicDailyMapUuid.value = null;
    const tm2RpgClassicHistory = useStorage('tm2RpgClassicHistory', null);
    tm2RpgClassicHistory.value = null;
    const tm2TrialClassicDailyMapUuid = useStorage('tm2TrialClassicDailyMapUuid', null);
    tm2TrialClassicDailyMapUuid.value = null;
    const tm2TrialClassicHistory = useStorage('tm2TrialClassicHistory', null);
    tm2TrialClassicHistory.value = null;
    const tmTrialClassicDailyMapUuid = useStorage('tmTrialClassicDailyMapUuid', null);
    tmTrialClassicDailyMapUuid.value = null;
    const tmnfRpgClassicDailyMapUuid = useStorage('tmnfRpgClassicDailyMapUuid', null);
    tmnfRpgClassicDailyMapUuid.value = null;
    const tmnfRpgClassicHistory = useStorage('tmnfRpgClassicHistory', null);
    tmnfRpgClassicHistory.value = null;
    const tmnfTrialClassicDailyMapUuid = useStorage('tmnfTrialClassicDailyMapUuid', null);
    tmnfTrialClassicDailyMapUuid.value = null;
    const tmnfTrialClassicHistory = useStorage('tmnfTrialClassicHistory', null);
    tmnfTrialClassicHistory.value = null;
});
</script>
