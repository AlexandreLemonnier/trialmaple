<template>
    <Transition name="fade" appear>
        <div class="flex flex-col gap-4 items-center p-2 lg:p-4 border bg-win-background rounded-lg">
            <div v-if="imagesLoaded" class="flex gap-2 items-center text-5xl lg:text-6xl xl:text-7xl">
                <span>woho</span>
                <img :src="smirkcat" alt="smirkcat" class="h-[1em]" />
                <img :src="thumbsup" alt="thumbsup" class="h-[1em]" />
            </div>
            <div class="flex flex-col items-center">
                <span>You solved TrialMaple #{{ dailyMapNumber }} in <strong>{{ Object.keys(history).length }} guesses</strong>! 🎉 Other players needed an average of <strong>{{ playersAverageScore }} guesses</strong>.</span>
            </div>
            <ShareButton :daily-map-number="dailyMapNumber" :history />
        </div>
    </Transition>
</template>

<script setup lang="ts">
import smirkcat from '#/assets/smirkcat.png';
import thumbsup from '#/assets/thumbsup.png';
import ShareButton from '#/components/ShareButton.vue';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';

const appStore = useAppStore();
const { history, dailyMapNumber, playersAverageScore } = storeToRefs(appStore);

const imagesLoaded = ref(false);
const smirkRef = ref<HTMLImageElement | null>(null);
const thumbRef = ref<HTMLImageElement | null>(null);

async function waitForImage(img: HTMLImageElement): Promise<void> {
    return new Promise((resolve) => {
        if (img.complete) {
            resolve();
        } else {
            img.addEventListener('load', () => resolve(), { once: true });
            img.addEventListener('error', () => resolve(), { once: true });
        }
    });
}

onMounted(async () => {
    const imgs = [smirkRef.value, thumbRef.value].filter(Boolean) as HTMLImageElement[];
    await Promise.all(imgs.map(waitForImage));
    imagesLoaded.value = true;
});

</script>
