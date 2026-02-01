<template>
    <Modal title="Discord server" v-model="isOpen" :on-close="setHasBeenSeen">
        <div class="flex flex-col gap-2 mt-3 text-sm text-neutral-300">
            <p>Join the Discord server to share your results and keep up with the latest updates!</p>
            <a class="underline w-fit" :href="discordLink" target="_blank">{{ discordLink }}</a>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Modal from '#/components/modal/Modal.vue';
import { useModalStore } from '#/stores/modalStore';

const { modals } = useModalStore();
const isOpen = defineModel<boolean>();

const discordLink = 'http://discord.gg/SqJYDMvxQz';

function setHasBeenSeen() {
    modals.NEW_DISCORD = true;
}

</script>
