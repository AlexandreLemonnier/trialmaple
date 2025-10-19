<template>
    <div class="absolute top-0 left-0 flex min-h-screen w-screen">
        <Gutter />
        <div class="flex flex-col w-full lg:w-3/5">
          <Header />
          <main>
            <slot></slot>
          </main>
        </div>
        <Gutter />
    </div>
</template>

<script setup lang="ts">
import Gutter from '#/components/layout/Gutter.vue';
import Header from '#/components/layout/Header.vue';
</script>