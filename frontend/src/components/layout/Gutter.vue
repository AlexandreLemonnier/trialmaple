<template>
    <div class="hidden md:block w-1/5 bg-size-[100%_auto] bg-repeat-y bg-top opacity-70 grayscale-20 brightness-[0.9] dark:brightness-[0.8]"
         :style="gutterStyle"></div>
</template>

<script setup lang="ts">
import { Route } from '#/router/Route';
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const gutterImage = computed(() => {
    switch (route.name as Route) {
        case Route.TMNF_TRIAL_CLASSIC_MODE:
            return 'tmnf-trial.jpg';
        case Route.TMNF_RPG_CLASSIC_MODE:
            return 'tmnf-rpg.png';
        case Route.TM2_TRIAL_CLASSIC_MODE:
            return 'tm2-trial.png';
        default:
            return 'tmnf-trial.jpg';
    }
});

const gutterStyle = computed(() => ({
    backgroundImage: `url('/side_images/${gutterImage.value}')`
}));

</script>
