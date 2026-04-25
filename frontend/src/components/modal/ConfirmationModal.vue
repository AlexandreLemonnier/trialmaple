<template>
    <Modal title="Confirmation" v-model="isOpen">
        <slot></slot>
        <div class="flex justify-between mt-2">
            <Button label="No" icon-name="x" @click="onNoClick" />
            <Button label="Yes" icon-name="check" @click="onYesClick" />
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import Modal from '#/components/modal/Modal.vue';

const { yesAction } = defineProps<{
    yesAction(): void;
}>();

const isOpen = defineModel<boolean>();

function onYesClick() {
    yesAction();
    isOpen.value = false;
}

function onNoClick() {
    isOpen.value = false;
}

</script>
