<template>
    <img v-if="user" :src="getDiscordAvatarUrl(user.discordId, user.avatar, user.discriminator)"
         :alt="`${user.username} avatar`"
         class="w-6 h-6 md:w-10 md:h-10 rounded-full border-2 border-discord object-cover shadow-sm" />
</template>

<script setup lang="ts">
import { useAppStore } from '#/stores/appStore.js';
import { getDiscordAvatarUrl } from '#/utils/discord';

const { user } = useAppStore();

</script>
