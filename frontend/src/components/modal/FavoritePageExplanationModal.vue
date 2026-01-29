<template>
    <Modal title="Favorite game mode" v-model="isOpen" :on-close="setHasBeenSeen">
        <div class="flex flex-col gap-2 mt-3 text-sm text-neutral-300">
            <p>Choose your favorite game mode!</p>
            <p>You will be automatically redirected to its dedicated page from <strong>{{ baseUrl }}</strong></p>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Modal from '#/components/modal/Modal.vue';
import { useModalStore } from '#/stores/modalStore';

const { modals } = useModalStore();
const isOpen = defineModel<boolean>();
const baseUrl = globalThis.location.origin;

function setHasBeenSeen() {
    modals.FAVORITE_PAGE = true;
}

</script>
