<template>
    <GeoguessrGame game-mode="GEOGUESSR_TM2020_RPG"
                   game-mode-display-name="TM2020 RPG Geoguessr"
                   history-storage-key="tm2020RpgGeoguessrHistory"
                   daily-map-uuid-storage-key="tm2020RpgGeoguessrDailyMapUuid" />
</template>

<script setup lang="ts">
import GeoguessrGame from '#/views/GeoguessrGame.vue';
</script>
