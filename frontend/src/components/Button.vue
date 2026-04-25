<template>
    <button type="button" class="flex items-center gap-1.5 lg:gap-2 text-sm lg:text-base rounded-full border border-app-border py-1 px-3 bg-button cursor-pointer hover:scale-105 transition-transform">
        <Icon v-if="iconName && iconPosition === 'left'" :class="iconClass" :name="iconName" :size="iconSize" />
        <span v-if="label">{{ label }}</span>
        <Icon v-if="iconName && iconPosition === 'right'" :class="iconClass" :name="iconName" :size="iconSize" />
    </button>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import type { IconName } from '#/types/IconName';
import type { Size } from '#/types/Size';
const { iconSize = 'sm', iconPosition = 'left' } = defineProps<{
    label?: string;
    iconName?: IconName;
    iconSize?: Size;
    iconPosition?: 'left' | 'right';
    iconClass?: string;
}>();

</script>
