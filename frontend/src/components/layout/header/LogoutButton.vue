<template>
    <Button class="bg-logout-button text-primary-white"
            label="Sign out"
            icon-name="logout"
            icon-size="sm"
            @click="logout" />
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import { useAppStore } from '#/stores/appStore';
import { storeToRefs } from 'pinia';

const { user } = storeToRefs(useAppStore());

function logout() {
    localStorage.removeItem('auth_token');
    user.value = null;
}
</script>
