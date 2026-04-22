<template>
    <Transition name="fade" appear>
        <div class="w-full md:w-auto bg-card-background p-2 rounded-xl shadow-md">
            <div class="relative w-full md:w-100 lg:w-160 aspect-video">
                <!-- Lock -->
                <Transition name="fade">
                    <div v-if="showLockedState" class="absolute inset-0 z-10 flex items-center justify-center bg-black/40 backdrop-blur-sm rounded-lg border border-app-border">
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
                     class="absolute inset-0 w-full h-full object-cover rounded-lg border border-app-border"
                     :src
                     :alt="`Hint n°${number}`"
                     @load="onLoad" />
            </div>
        </div>
    </Transition>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import Loader from '#/components/Loader.vue';
import { onMounted, ref } from 'vue';

const props = defineProps<{
    src: string;
    number: number;
}>();

const isLoading = ref(true);

function onLoad() {
    isLoading.value = false;
}

const showLockedState = ref(true);
const isUnlocking = ref(false);

const TIME_BEFORE_UNLOCK = 300;
const UNLOCK_ANIMATION_DURATION = 700;

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
