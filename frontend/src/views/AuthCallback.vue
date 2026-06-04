<template>
    <div class="flex flex-col items-center justify-center h-full">
        <div v-if="error" class="flex flex-col items-center">
            <span class="text-error font-bold">{{ error }}</span>
            <button class="cursor-pointer underline mt-2 inline-block" type="button" @click="router.push({ name: Route.HOME })">Return to home</button>
        </div>
        <Loader v-else loading-reason="Connecting to Discord" />
    </div>
</template>

<script setup lang="ts">
import Loader from '#/components/Loader.vue';
import { useAuthApi } from '#/composables/api/useAuthApi';
import { Route } from '#/router/Route';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const error = ref<string | null>(null);

const authApi = useAuthApi();

onMounted(async () => {
    const code = route.query.code as string;

    if (!code) {
        error.value = 'No authorization code provided.';
        return;
    }

    try {
        const response = await authApi.loginWithDiscord(code);

        localStorage.setItem('auth_token', response.token);
        // TODO Set user info in a store
        // TODO Also load user from API if already has a valid token

        router.push({ name: Route.HOME });
    } catch (err) {
        error.value = err instanceof Error ? err.message : 'Failed to authenticate.';
    }
});
</script>
