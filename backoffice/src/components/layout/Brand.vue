<template>
    <div class="flex items-center"
         :class="compact ? 'w-9 h-9 text-sm gap-2.5' : 'gap-3.5'">
        <span class="inline-flex flex-none items-center justify-center rounded-lg bg-brand-primary font-extrabold text-primary-white"
              :class="compact ? 'w-9 h-9 text-sm' : 'h-11 w-11'">TM</span>
        <div>
            <p class="text-xs font-bold uppercase text-text-muted">TM-Maple</p>
            <h1 class="font-bold"
                :class="compact ? 'text-lg' : 'text-3xl'">Backoffice</h1>
        </div>
    </div>
</template>

<script lang="ts" setup>
defineProps<{
    compact?: boolean;
}>();
</script>
