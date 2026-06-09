<template>
    <Modal title="Track Your Progress 🏆" v-model="isOpen">
        <div class="flex flex-col gap-4 text-sm">
            <p>You are currently playing as guest. Sign in with Discord to save your daily scores and unlock your personal statistics dashboard!</p>
            <div class="flex justify-between">
                <Button label="Play as Guest" @click="isOpen = false" />
                <LoginButton />
            </div>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import LoginButton from '#/components/layout/header/LoginButton.vue';
import Modal from '#/components/modal/Modal.vue';

const isOpen = defineModel<boolean>();

</script>
