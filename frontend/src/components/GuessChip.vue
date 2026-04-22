<template>
    <div class="flex items-center gap-1 lg:gap-2 rounded-full border bg-card-background border-app-border px-2 lg:px-2.5 lg:py-0.5">
        <Icon v-if="success" class="text-success" name="check" size="sm" />
        <Icon v-else class="text-error" name="x" size="sm" />
        <span>{{ name }}</span>
    </div>
</template>

<script setup lang="ts">
import Icon from './Icon.vue';

defineProps<{
    name: string;
    success: boolean;
}>();
</script>
