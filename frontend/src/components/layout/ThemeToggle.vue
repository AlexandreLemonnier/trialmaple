<template>
    <div role="switch"
         :aria-checked="theme === 'dark'"
         tabindex="0"
         @click="toggleTheme"
         class="relative flex items-center w-14 h-7 px-1 rounded-full cursor-pointer bg-theme-toggle-background transition-colors duration-300">
        <Icon name="sun" size="sm" class="z-10" />
        <Icon name="moon" size="sm" class="z-10 ml-auto text-black" />
        <span class="absolute left-1 top-1 w-5 h-5 rounded-full bg-theme-toggle-knob shadow-md transition-transform duration-300"
              :class="theme === 'dark' ? 'translate-x-7' : 'translate-x-0'" />
    </div>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import { useTheme } from '#/composables/useTheme';

const { theme, toggleTheme } = useTheme();

</script>
