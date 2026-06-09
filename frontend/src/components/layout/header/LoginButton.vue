<template>
    <Button class="bg-login-button"
            scale
            label="Sign in"
            icon-name="login"
            icon-size="sm"
            @click="loginWithDiscord" />
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import { Route } from '#/router/Route';

const discordClientId = '1511852531100160010';
const redirectUri = encodeURIComponent(`${globalThis.location.origin}${Route.AUTH_CALLBACK}`);

function loginWithDiscord() {
    const discordAuthUrl = `https://discord.com/api/oauth2/authorize?client_id=${discordClientId}&redirect_uri=${redirectUri}&response_type=code&scope=identify`;
    globalThis.location.href = discordAuthUrl;
}
</script>
