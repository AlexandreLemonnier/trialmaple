<template>
    <Modal title="About TM-Maple" v-model="isOpen">
        <div class="flex flex-col gap-2 mt-3 text-sm">
            <p><strong>TM-Maple is a daily guessing game</strong> based on different TrackMania map lists.</p>
            <p>A new map becomes available every day at <strong>00:00 CET/CEST</strong> for each game mode.</p>
            <template v-if="gameModeInfo.mode !== 'NONE'">
                <hr class="opacity-20" />
                <p v-if="gameModeInfo.mode === 'CLASSIC'">Pick a map and click <strong>"Guess"</strong> to get hints: does today's map have more or fewer points? Which authors match? Repeat until you find it!</p>
                <p v-else-if="gameModeInfo.mode === 'GEOGUESSR'">Pick a map, click <strong>"Guess"</strong>, and either win or unlock next picture!</p>
                <hr class="opacity-20" />
                <p><strong>About current game mode</strong></p>
                <template v-if="gameModeInfo">
                    <p v-if="gameModeInfo.excludeUnfinished">Note that <strong>unfinished maps</strong> are not included.</p>
                    <p v-if="gameModeInfo.autoUpdate">Maps data are automatically updated everyday at midnight.</p>
                    <p v-if="gameModeInfo.mode === 'GEOGUESSR'">A map can be picked multiple times with different pictures (or the same ones).</p>
                </template>
            </template>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Modal from '#/components/modal/Modal.vue';
import { Route } from '#/router/Route';
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const isOpen = defineModel<boolean>();

const route = useRoute();

const GAME_MODE_CONFIG: Record<Route, {
    mode: 'CLASSIC' | 'GEOGUESSR' | 'NONE';
    excludeUnfinished?: boolean;
    autoUpdate?: boolean;
}> = {
    [Route.HOME]: {
        mode: 'NONE'
    },
    [Route.TMNF_TRIAL_CLASSIC_MODE]: {
        mode: 'CLASSIC',
        excludeUnfinished: true
    },
    [Route.TMNF_RPG_CLASSIC_MODE]: {
        mode: 'CLASSIC',
        autoUpdate: true
    },
    [Route.TM2_TRIAL_CLASSIC_MODE]: {
        mode: 'CLASSIC',
        autoUpdate: true
    },
    [Route.TM2_RPG_CLASSIC_MODE]: {
        mode: 'CLASSIC',
        autoUpdate: true
    },
    [Route.TM2020_TRIAL_CLASSIC_MODE]: {
        mode: 'CLASSIC',
        autoUpdate: true
    },
    [Route.TM2020_RPG_GEOGUESSR_MODE]: {
        mode: 'GEOGUESSR'
    }
};

const gameModeInfo = computed(() =>
    GAME_MODE_CONFIG[route.name as Route]
);

</script>
