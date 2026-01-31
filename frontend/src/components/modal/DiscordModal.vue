<template>
    <Modal title="Discord server" v-model="isOpen">
        <div class="flex flex-col gap-2 mt-3 text-sm text-neutral-300">
            <p>Join the Discord server to share your results and keep up with the latest updates!</p>
            <a class="underline w-fit" :href="discordLink" target="_blank">{{ discordLink }}</a>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Modal from '#/components/modal/Modal.vue';

const isOpen = defineModel<boolean>();

const discordLink = 'http://discord.gg/SqJYDMvxQz';

</script>
