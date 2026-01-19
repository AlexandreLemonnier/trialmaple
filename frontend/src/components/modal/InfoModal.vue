<template>
    <Modal title="About TM-Maple" v-model="isOpen">
        <div class="flex flex-col gap-2 mt-3 text-sm text-neutral-300">
            <p><strong>TM-Maple is a daily guessing game</strong> based on different TrackMania map lists.</p>
            <p>A new map becomes available every day at <strong>00:00 CET/CEST</strong> for each game mode.</p>
            <hr class="opacity-20" />
            <p>Pick a map and click <strong>"Guess"</strong> to get hints: does today's map have more or fewer points? Which authors match? Repeat until you find it!</p>
            <hr class="opacity-20" />
            <p><strong>About current game mode</strong></p>
            <template v-if="gameModeInfo">
                <p v-if="gameModeInfo.excludeUnfinished">Note that <strong>unfinished maps</strong> are not included.</p>
                <p v-if="gameModeInfo.updatedAt">Maps data last updated on <strong>{{ gameModeInfo.updatedAt }}</strong>.</p>
                <p v-if="gameModeInfo.autoUpdate">Maps data are automatically updated everyday at midnight.</p>
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
    excludeUnfinished?: boolean;
    updatedAt?: string;
    autoUpdate?: boolean;
}> = {
    [Route.TMNF_TRIAL_CLASSIC_MODE]: {
        excludeUnfinished: true,
        updatedAt: '19 January 2026'
    },
    [Route.TMNF_RPG_CLASSIC_MODE]: {
        autoUpdate: true
    }
};

const gameModeInfo = computed(() =>
    GAME_MODE_CONFIG[route.name as Route]
);

</script>
