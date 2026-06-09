<template>
    <ConfirmationModal v-model="isConfirmationOpen" :yes-action="giveUp">
        <p>Are you sure you want to give up? Your current progress will be lost.</p>
    </ConfirmationModal>
    <Button class="bg-giveup-button text-primary-white"
            pill
            scale
            label="Give Up"
            icon-name="flag"
            @click="confirmGiveUp" />
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import ConfirmationModal from '#/components/modal/ConfirmationModal.vue';
import { useGuessApi } from '#/composables/api/useGuessApi';
import type { Answer } from '#/types/api/answer';
import type { GameMode } from '#/types/api/gameMode';
import { ref } from 'vue';
const { gameMode } = defineProps<{
    gameMode: GameMode;
}>();

const emit = defineEmits<(e: 'done', answer: Answer) => void>();

const isConfirmationOpen = ref(false);

function confirmGiveUp() {
    isConfirmationOpen.value = true;
}

const guessRepository = useGuessApi();

async function giveUp() {
    const answer = await guessRepository.giveUp(gameMode);
    emit('done', answer);
}

</script>
