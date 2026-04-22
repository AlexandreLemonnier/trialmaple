<template>
    <Transition name="fade" appear>
        <div class="w-full md:w-auto bg-card-background p-2 rounded-xl shadow-md">
            <div class="relative w-full md:w-100 lg:w-160 aspect-video">
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
import Loader from '#/components/Loader.vue';
import { ref } from 'vue';

const props = defineProps<{
    src: string;
    number: number;
}>();

const isLoading = ref(true);

function onLoad() {
    isLoading.value = false;
}
</script>
