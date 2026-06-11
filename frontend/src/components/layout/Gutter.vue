<template>
    <div class="hidden md:block w-1/5 bg-app-background bg-size-[100%_auto] bg-repeat-y bg-top opacity-70 grayscale-20 brightness-[0.9] dark:brightness-[0.5]"
         :style="gutterStyle"></div>
</template>

<script setup lang="ts">
import { Route } from '#/router/Route';
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const IMAGES = [
    'tmnf-trial.jpg',
    'tmnf-rpg.png',
    'tm2-trial.png',
    'tm2-rpg.png',
    'tm2020-trial.png',
    'tm2020-rpg.png',
    'tm-all.png'
] as const;

type GutterImage = typeof IMAGES[number];

const gutterImage = computed<GutterImage>(() => {
    switch (route.name as Route) {
        case Route.TMNF_TRIAL_CLASSIC_MODE:
        case Route.TMNF_TRIAL_BLUR_MODE:
        case Route.TMNF_TRIAL_ZOOM_MODE:
            return 'tmnf-trial.jpg';
        case Route.TMNF_RPG_CLASSIC_MODE:
            return 'tmnf-rpg.png';
        case Route.TM2_TRIAL_CLASSIC_MODE:
            return 'tm2-trial.png';
        case Route.TM2_RPG_CLASSIC_MODE:
            return 'tm2-rpg.png';
        case Route.TM2020_TRIAL_CLASSIC_MODE:
            return 'tm2020-trial.png';
        case Route.TM2020_RPG_CLASSIC_MODE:
        case Route.TM2020_RPG_GEOGUESSR_MODE:
            return 'tm2020-rpg.png';
        default:
            return 'tm-all.png';
    }
});

const gutterStyle = computed(() => ({
    backgroundImage: `url('/side_images/${gutterImage.value}')`
}));

onMounted(() => {
    // Preload gutter images for smooth page transitions
    IMAGES.forEach((img) => {
        const href = `/side_images/${img}`;

        if (document.querySelector(`link[href="${href}"]`)) return;

        const link = document.createElement('link');
        link.rel = 'preload';
        link.as = 'image';
        link.href = href;
        document.head.appendChild(link);
    });
});

</script>
