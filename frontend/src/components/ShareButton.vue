<template>
    <div class="flex gap-2 items-center">
        <button type="button"
                class="text-md lg:text-lg xl:text-xl rounded-md bg-share-button border border-app-border py-1 px-2 cursor-pointer hover:scale-105 transition-transform"
                :class="resultCopyClass[resultCopyStatus]"
                @click="copyHistoryResult">
            {{ resultCopyMessage[resultCopyStatus] }}
        </button>
    </div>
</template>

<script setup lang="ts">
import { createGameStore } from '#/stores/gameStore';
import type { GameMode } from '#/types/api/gameMode';
import { copyToClipboard } from '#/utils/copyToClipboard';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const { formatResult, gameMode, gameModeDisplayName, historyStorageKey, dailyMapUuidStorageKey } = defineProps<{
    formatResult(): string;
    gameMode: GameMode;
    gameModeDisplayName: string;
    historyStorageKey: string;
    dailyMapUuidStorageKey: string;
}>();

const gameStore = createGameStore(gameMode, historyStorageKey, dailyMapUuidStorageKey)();
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

function getTitle(guessesCount: number) {
    if (dailyMapNumber.value) {
        return `${gameModeDisplayName} #${dailyMapNumber.value} - ${guessesCount} ${guessesCount <= 1 ? 'guess' : 'guesses'} 😼👍`;
    }
    return '';
}

async function copyHistoryResult() {
    try {
        const guessesCount = Object.keys(history.value).length;
        const result = getTitle(guessesCount) + formatResult();
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
