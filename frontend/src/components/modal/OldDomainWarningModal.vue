<template>
    <Modal title="Warning" v-model="isOpen">
        <div class="flex flex-col gap-2 mt-3 text-sm">
            <p>You are using the old domain name "trialmaple.com". It will expire on 14th December 2026</p>
            <p>Please switch to "<strong>tmmaple.com</strong>" from now on.</p>
        </div>
    </Modal>
</template>

<script setup lang="ts">
import Modal from '#/components/modal/Modal.vue';

const isOpen = defineModel<boolean>();

</script>
