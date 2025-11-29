<template>
    <Transition name="modal">
        <div v-if="isModalOpen" class="fixed inset-0 z-9999 flex items-center justify-center bg-black/60 p-4" @click.self="isModalOpen = false">
            <div class="bg-neutral-900 text-neutral-100 rounded-2xl p-5 max-w-md w-full border border-white/10 shadow-xl transition-all duration-150 transform">
                <div class="flex items-start justify-between gap-4">
                    <h2 class="text-lg font-black">{{ title }}</h2>
                    <Icon class="cursor-pointer" name="x" size="sm" @click="isModalOpen = false" />
                </div>
                <slot></slot>
            </div>
        </div>
    </Transition>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';

defineProps<{
    title: string;
}>();

const isModalOpen = defineModel<boolean>();
</script>
