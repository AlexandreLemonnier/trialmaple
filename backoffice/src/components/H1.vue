<template>
    <h1 class="font-bold"
        :class="compact ? 'text-lg' : 'text-3xl'">
        <slot></slot>
    </h1>
</template>

<script lang="ts" setup>
defineProps<{
    compact?: boolean;
}>();
</script>
