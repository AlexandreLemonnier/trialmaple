<template>
    <div class="absolute top-0 left-0 flex min-h-screen overflow-x-hidden w-full text-app-text">
        <OldDomainWarningModal v-model="isFromOldDomainName" />
        <Gutter />
        <div class="flex flex-col w-full md:w-3/5 bg-app-background min-h-screen">
            <Header />
            <main class="mx-2 mb-4 flex flex-1 flex-col">
                <slot></slot>
            </main>
            <footer class="flex flex-col gap-2 mt-auto mx-2 lg:mx-4 mb-2 text-sm italic">
                <Button v-if="route.name !== Route.HOME"
                        class="w-fit"
                        label="Back to Home"
                        icon-name="house"
                        @click="router.push({ name: Route.HOME })" />
                <span class="self-center">Made by <strong>Arsolight</strong></span>
            </footer>
        </div>
        <Gutter />
    </div>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import Gutter from '#/components/layout/Gutter.vue';
import Header from '#/components/layout/Header.vue';
import OldDomainWarningModal from '#/components/modal/OldDomainWarningModal.vue';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const { isFromOldDomainName } = storeToRefs(useAppStore());

</script>
