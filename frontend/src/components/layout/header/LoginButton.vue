<template>
    <Button class="bg-login-button text-primary-white"
            scale
            label="Sign in"
            icon-name="login"
            icon-size="sm"
            @click="loginWithDiscord" />
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import { useEnv } from '#/composables/useEnv';
import { Route } from '#/router/Route';

const { DISCORD_CLIENT_ID } = useEnv();

const redirectUri = encodeURIComponent(`${globalThis.location.origin}${Route.AUTH_CALLBACK}`);

function loginWithDiscord() {
    const params = new URLSearchParams({
        client_id: DISCORD_CLIENT_ID,
        redirect_uri: redirectUri,
        response_type: 'code',
        scope: 'identify'
    });
    globalThis.location.assign(`https://discord.com/api/oauth2/authorize?${params.toString()}`);
}
</script>
