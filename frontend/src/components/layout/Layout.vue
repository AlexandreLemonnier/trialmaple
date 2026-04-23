<template>
    <div class="absolute top-0 left-0 flex min-h-screen overflow-x-hidden w-full">
        <Gutter />
        <div class="flex flex-col w-full md:w-3/5 bg-app-background text-app-text min-h-screen">
            <Header />
            <main class="mx-2 mb-4 flex flex-1 flex-col">
                <slot></slot>
            </main>
            <footer class="flex flex-col mt-auto mx-2 lg:mx-4 mb-2 text-sm italic">
                <button v-if="route.name !== Route.HOME"
                        type="button"
                        class="flex items-center w-fit gap-1.5 lg:gap-2 text-sm lg:text-base rounded-full border-2 border-app-border py-1 px-3 bg-button cursor-pointer hover:scale-105 transition-transform"
                        @click="router.push({ name: Route.HOME })">
                    <Icon name="house" size="sm" />
                    <span class="hidden xs:block">Back to home</span>
                </button>
                <span class="self-center">Made by <strong>Arsolight</strong></span>
            </footer>
        </div>
        <Gutter />
    </div>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import Gutter from '#/components/layout/Gutter.vue';
import Header from '#/components/layout/Header.vue';
import { Route } from '#/router/Route';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

</script>
