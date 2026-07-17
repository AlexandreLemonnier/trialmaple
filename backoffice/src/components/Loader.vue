<template>
    <div class="flex flex-col items-center justify-center gap-4">
        <output class="size-10 block rounded-full border-4 border-app-border border-t-brand-primary animate-spin"
                aria-label="Loading"></output>

        <span v-if="loadingReason" class="text-text-muted font-medium">
            {{ loadingReason }}
        </span>
    </div>
</template>

<script setup lang="ts">
defineProps<{
    loadingReason?: string;
}>();
</script>
