<template>
    <button class="text-lg lg:text-xl xl:text-2xl rounded-full border-2 border-app-border py-2 px-4 bg-guess-button/20 text-guess-button cursor-pointer hover:scale-105 transition-transform"
            type="button"
            :inert="!maps.length || !selectedMap || inert">
        <span v-if="!isLoading">Guess</span>
        <Loader v-else />
    </button>
</template>

<script setup lang="ts" generic="T">
import Loader from '#/components/Loader.vue';

defineProps<{
    maps: T[];
    selectedMap: T | undefined;
    inert?: boolean;
    isLoading?: boolean;
}>();
</script>
