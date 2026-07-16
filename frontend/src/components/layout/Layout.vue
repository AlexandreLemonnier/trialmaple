<template>
    <div class="absolute top-0 left-0 flex min-h-screen overflow-clip w-full text-app-text-primary">
        <OldDomainWarningModal v-model="isFromOldDomainName" />
        <Gutter />
        <div class="flex flex-col w-full md:w-3/5 bg-app-background min-h-screen">
            <Header />
            <main class="mx-2 mb-4 flex flex-1 flex-col">
                <slot></slot>
            </main>
            <Footer />
        </div>
        <Gutter />
    </div>
</template>

<script setup lang="ts">
import Footer from '#/components/layout/footer/Footer.vue';
import Gutter from '#/components/layout/Gutter.vue';
import Header from '#/components/layout/header/Header.vue';
import OldDomainWarningModal from '#/components/modal/OldDomainWarningModal.vue';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';

const { isFromOldDomainName } = storeToRefs(useAppStore());

</script>
