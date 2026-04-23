<template>
    <div class="absolute top-0 left-0 flex min-h-screen overflow-x-hidden w-full">
        <Gutter />
        <div class="flex flex-col w-full md:w-3/5 bg-app-background text-app-text min-h-screen">
            <Header />
            <main class="mx-2 mb-4 flex flex-1 flex-col">
                <slot></slot>
            </main>
            <footer class="flex flex-col mt-auto mx-2 lg:mx-4 mb-2 text-sm italic">
                <Button v-if="route.name !== Route.HOME"
                        class="w-fit"
                        label="Back to home"
                        icon-name="house"
                        icon-size="sm"
                        icon-position="left"
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
import { Route } from '#/router/Route';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

</script>
