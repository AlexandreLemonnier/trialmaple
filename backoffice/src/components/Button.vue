<template>
    <Button :label
            :icon
            :disabled
            :loading
            @click="action"
            class="bg-brand-primary hover:bg-button-background/60 text-button-text font-bold border-button-border" />
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
