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
});
</script>
