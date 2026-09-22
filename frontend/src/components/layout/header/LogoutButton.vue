<template>
    <Button class="bg-logout-button text-primary-white"
            label="Sign out"
            icon-name="logout"
            icon-size="sm"
            @click="logout" />
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import { useAuth } from '#/composables/useAuth';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';

const { user } = storeToRefs(useAppStore());
const { clearAuthToken } = useAuth();

function logout() {
    clearAuthToken(true);
    user.value = null;
}
</script>
