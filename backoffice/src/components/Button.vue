<template>
    <Button :label
            :icon
            :disabled
            :loading
            @click="action"
            class="bg-brand-primary text-button-text font-bold border-button-border"
            :class="[!disabled && 'hover:bg-button-background/60', disabled && 'cursor-not-allowed']" />
</template>

<script setup lang="ts">
import Button from 'primevue/button';
defineProps<{
    label?: string;
    icon?: string;
    disabled?: boolean;
    loading?: boolean;
    action(): void;
}>();

</script>
