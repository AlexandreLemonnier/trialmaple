<template>
    <div class="absolute top-0 left-0 flex min-h-screen overflow-x-hidden w-full">
        <Gutter />
        <div class="flex flex-col w-full md:w-3/5 bg-app-background text-app-text">
            <Header />
            <NavigationBar />
            <main class="mx-2 mb-4">
                <slot></slot>
            </main>
            <footer class="flex flex-col mt-auto mb-2 text-center text-sm italic">
                <span>Made by <strong>Arsolight</strong></span>
                <span>Contact on Discord</span>
            </footer>
        </div>
        <Gutter />
    </div>
</template>

<script setup lang="ts">
import Gutter from '#/components/layout/Gutter.vue';
import Header from '#/components/layout/Header.vue';
import NavigationBar from '#/components/layout/NavigationBar.vue';
</script>
