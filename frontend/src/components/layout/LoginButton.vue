<template>
    <Button class="bg-discord/80 text-primary-white"
            label="Sign in"
            icon-size="md"
            @click="loginWithDiscord" />
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import { Route } from '#/router/Route';

const discordClientId = '1511852531100160010';
const redirectUri = encodeURIComponent(`${globalThis.location.origin}${Route.AUTH_CALLBACK}`);

const loginWithDiscord = () => {
    const discordAuthUrl = `https://discord.com/api/oauth2/authorize?client_id=${discordClientId}&redirect_uri=${redirectUri}&response_type=code&scope=identify`;
    globalThis.location.href = discordAuthUrl;
};
</script>
