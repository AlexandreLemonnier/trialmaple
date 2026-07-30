<template>
    <a :href="url"
       target="_blank"
       rel="noopener noreferrer"
       class="text-text-muted hover:text-text-muted/60 transition-colors"
       title="View on Trackmania Exchange"
       @click.stop>
        <i class="pi pi-external-link"></i>
    </a>
</template>

<script lang="ts" setup>
defineProps<{
    url: string
}>();
</script>
