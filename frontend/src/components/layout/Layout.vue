<template>
    <div class="absolute top-0 left-0 flex min-h-screen w-screen">
        <Gutter />
        <div class="w-3/5 lg:px-4">
          <slot></slot>
        </div>
        <Gutter />
    </div>
</template>

<script setup lang="ts">
import Gutter from '#/components/layout/Gutter.vue';
</script>