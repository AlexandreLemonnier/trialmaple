<template>
    <button type="button"
            class="flex items-center justify-center gap-1.5 lg:gap-2 w-fit text-sm lg:text-base text-brand-primary border border-button-border py-1 px-3 bg-button-background hover:bg-button-background/40 rounded-lg cursor-pointer"
            :disabled>
        <Icon v-if="iconName && iconPosition === 'left'" :class="iconClass" :name="iconName" :size="iconSize" />
        <span v-if="label" class="font-extrabold">{{ label }}</span>
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
    disabled?: boolean;
}>();

</script>
