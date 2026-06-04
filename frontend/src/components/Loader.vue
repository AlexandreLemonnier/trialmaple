<template>
    <div class="flex flex-col items-center gap-4">
        <div class="flex items-center gap-1">
            <span class="size-1.5 rounded-full bg-current animate-bounce [animation-delay:-0.3s]" />
            <span class="size-1.5 rounded-full bg-current animate-bounce [animation-delay:-0.15s]" />
            <span class="size-1.5 rounded-full bg-current animate-bounce" />
        </div>
        <span v-if="loadingReason">{{ loadingReason }}</span>
    </div>
</template>

<script setup lang="ts">
defineProps<{
    loadingReason?: string;
}>();
</script>
