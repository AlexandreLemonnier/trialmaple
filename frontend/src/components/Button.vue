<template>
    <button type="button" class="flex gap-2 items-center rounded-md px-2 py-1 cursor-pointer">
        <span v-if="label">{{ label }}</span>
        <Icon v-if="iconName" :name="iconName" size="md" />
    </button>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import type { IconName } from '#/types/IconName';
defineProps<{
    label?: string;
    iconName?: IconName;
}>();

</script>
