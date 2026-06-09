<template>
    <div class="flex gap-2 items-center">
        <Button :label="resultCopyMessage[resultCopyStatus]"
                scale
                :class="resultCopyClass[resultCopyStatus]"
                @click="copyHistoryResult" />
    </div>
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import { createGameStore } from '#/stores/gameStore';
import { GAME_MODE_DISPLAY_NAMES, type GameMode } from '#/types/api/gameMode';
import { copyToClipboard } from '#/utils/copyToClipboard';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const { formatResult, gameMode, storageKey } = defineProps<{
    formatResult(): string;
    gameMode: GameMode;
    storageKey: string;
}>();

const gameStore = createGameStore(gameMode, storageKey)();
const { historyContainsSuccess } = gameStore;
const { history, dailyMapNumber } = storeToRefs(gameStore);

type CopyStatus = 'NONE' | 'SUCCESS' | 'ERROR';

const resultCopyStatus = ref<CopyStatus>('NONE');

const resultCopyMessage: Record<CopyStatus, string> = {
    SUCCESS: 'Copied!',
    ERROR: 'Error',
    NONE: 'Share result'
};

const resultCopyClass: Record<CopyStatus, string> = {
    SUCCESS: 'text-confirmation-text italic',
    ERROR: 'text-error italic',
    NONE: ''
};

function getTitle(guessesCount: number, hasWon: boolean) {
    if (dailyMapNumber.value) {
        return `${GAME_MODE_DISPLAY_NAMES[gameMode]} #${dailyMapNumber.value} - ${guessesCount} ${guessesCount <= 1 ? 'guess' : 'guesses'} ${hasWon ? '😼👍' : '😿❌'}`;
    }
    return '';
}

async function copyHistoryResult() {
    try {
        const guessesCount = Object.keys(history.value).length;
        const hasWon = historyContainsSuccess();
        const result = getTitle(guessesCount, hasWon) + formatResult();
        resultCopyStatus.value = await copyToClipboard(result) ? 'SUCCESS' : 'ERROR';
    } catch (e) {
        console.error(e);
        resultCopyStatus.value = 'ERROR';
    }
    setTimeout(() => {
        resultCopyStatus.value = 'NONE';
    }, 1500);
}
</script>
