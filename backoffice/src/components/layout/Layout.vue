<template>
    <div class="min-h-screen text-text-primary" :class="isSignedIn ? 'grid min-h-screen grid-cols-1 md:grid-cols-[248px_minmax(0,1fr)]' : 'bg-app-background'">
        <Sidebar v-if="isSignedIn" />
        <slot></slot>
    </div>
</template>

<script setup lang="ts">
import Sidebar from '#/components/layout/Sidebar.vue';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { computed } from 'vue';

const { authState } = storeToRefs(useAppStore());
const isSignedIn = computed(() => authState.value === 'signed-in');

</script>
