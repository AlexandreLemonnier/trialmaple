<template>
    <Transition name="modal">
        <div v-if="isModalOpen" class="fixed inset-0 z-9999 flex items-center justify-center bg-black/60 p-4" @click.self="close">
            <div class="flex flex-col gap-2 bg-modal-background rounded-2xl p-5 w-full border border-white/10 shadow-xl transition-all duration-150 transform" :class="[maxSizeToClass(size)]">
                <div class="flex items-start justify-between gap-4">
                    <h2 class="text-lg font-black">{{ title }}</h2>
                    <Icon class="cursor-pointer" name="x" size="sm" @click="close" />
                </div>
                <slot></slot>
            </div>
        </div>
    </Transition>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import type { Size } from '#/types/Size';
import { maxSizeToClass } from '#/utils/sizeToClass';
import { onMounted, onUnmounted } from 'vue';

const { onClose, size = 'md' } = defineProps<{
    title?: string;
    onClose?(): void;
    size?: Size;
}>();

const isModalOpen = defineModel<boolean>();

function close() {
    isModalOpen.value = false;
    onClose?.();
}

function onKeydown(e: KeyboardEvent) {
    if (e.key === 'Escape' && isModalOpen.value) {
        close();
    }
}

onMounted(() => {
    globalThis.addEventListener('keydown', onKeydown);
});

onUnmounted(() => {
    globalThis.removeEventListener('keydown', onKeydown);
});

</script>
