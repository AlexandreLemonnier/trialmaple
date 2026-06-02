<template>
    <Transition name="fade" appear>
        <div class="w-full md:w-auto bg-card-background p-2 rounded-xl shadow-md">
            <div class="relative w-full md:w-100 lg:w-160 aspect-video overflow-hidden rounded-lg border border-app-border" style="container-type: inline-size">
                <!-- Lock -->
                <Transition name="fade">
                    <div v-if="showLockedState" class="absolute inset-0 z-10 flex items-center justify-center bg-black/40 backdrop-blur-sm">
                        <Icon class="transition-colors duration-300"
                              :class="[isUnlocking ? 'text-success animate-shake' : 'text-white']"
                              :name="isUnlocking ? 'lock-open' : 'lock'"
                              size="md" />
                    </div>
                </Transition>
                <!-- Loader -->
                <div v-if="isLoading" class="absolute inset-0 flex items-center justify-center bg-black/10 rounded-lg">
                    <Loader />
                </div>
                <!-- Image -->
                <img v-show="!isLoading"
                     class="absolute inset-0 w-full h-full object-cover rounded-lg border border-app-border cursor-zoom-in select-none transition-all duration-500 ease-in-out"
                     :style="{ filter: blurLevel ? `blur(${blurLevel}cqw)` : 'none' }"
                     :src
                     :alt="`Hint n°${number}`"
                     @load="onLoad"
                     @click="openModal"
                     @contextmenu.prevent
                     @dragstart.prevent />
            </div>
        </div>
    </Transition>
    <Modal v-model="isZoomOpen" size="full">
        <div class="flex items-center justify-center w-full h-full">
            <div class="relative w-full aspect-video max-h-[85vh] max-w-[calc(85vh*16/9)] mx-auto overflow-hidden rounded-lg border border-app-border shadow-2xl"
                 style="container-type: inline-size">
                <img class="absolute inset-0 w-full h-full object-cover cursor-zoom-out select-none"
                     :style="{ filter: blurLevel ? `blur(${blurLevel}cqw)` : 'none' }"
                     :src="src"
                     :alt="`Hint n°${number}`"
                     @click="isZoomOpen = false"
                     @contextmenu.prevent
                     @dragstart.prevent />
            </div>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import Loader from '#/components/Loader.vue';
import Modal from '#/components/modal/Modal.vue';
import { onMounted, ref } from 'vue';

const props = defineProps<{
    src: string;
    number: number;
    blurLevel?: number;
}>();

const isLoading = ref(true);
const isZoomOpen = ref(false);

function onLoad() {
    isLoading.value = false;
}

const showLockedState = ref(true);
const isUnlocking = ref(false);

const TIME_BEFORE_UNLOCK = 300;
const UNLOCK_ANIMATION_DURATION = 700;

function openModal() {
    if (!showLockedState.value && !isLoading.value) {
        isZoomOpen.value = true;
    }
}

function triggerLockAnimation() {
    isUnlocking.value = true;
    setTimeout(() => {
        isUnlocking.value = false;
        showLockedState.value = false;
    }, UNLOCK_ANIMATION_DURATION);
}

onMounted(() => {
    setTimeout(() => {
        triggerLockAnimation();
    }, TIME_BEFORE_UNLOCK);
});

</script>
