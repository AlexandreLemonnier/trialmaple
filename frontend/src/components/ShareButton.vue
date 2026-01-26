<template>
    <div class="flex gap-2 items-center">
        <button type="button"
                class="text-md lg:text-lg xl:text-xl rounded-sm bg-card-background border border-app-border py-1 px-2 cursor-pointer hover:scale-105 transition-transform"
                :class="resultCopyClass[resultCopyStatus]"
                @click="copyHistoryResult">
            {{ resultCopyMessage[resultCopyStatus] }}
        </button>
    </div>
</template>

<script setup lang="ts">
import { createGameStore } from '#/stores/appStore';
import type { DeltaHint } from '#/types/api/deltaHint';
import type { GameMode } from '#/types/api/gameMode';
import type { Guess } from '#/types/api/guess';
import { copyToClipboard } from '#/utils/copyToClipboard';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const { gameMode, gameModeDisplayName, guessProps, historyStorageKey, dailyMapUuidStorageKey } = defineProps<{
    gameMode: GameMode;
    gameModeDisplayName: string;
    guessProps: (keyof Guess)[];
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

const deltaHintEmoji: Record<DeltaHint, string> = {
    EQUAL: '🟩',
    LESS: '🔻',
    MORE: '🔺'
};

function hintToEmoji(hint: boolean | DeltaHint) {
    if (typeof hint === 'boolean') {
        return hint ? '🟩' : '🟥';
    }
    return deltaHintEmoji[hint];
}

function getTitle(guessesCount: number) {
    if (dailyMapNumber.value) {
        return `${gameModeDisplayName} #${dailyMapNumber.value} - ${guessesCount} ${guessesCount <= 1 ? 'guess' : 'guesses'} 😼👍`;
    }
    return '';
}

function formatGuess(guess: Guess) {
    let result = '';
    for (const prop of guessProps) {
        const guessHint = guess[prop];
        if (guessHint !== null && typeof guessHint !== 'boolean') {
            if (Array.isArray(guessHint)) {
                result += hintToEmoji(guessHint.some((hintPair) => hintPair.hint));
            } else {
                result += hintToEmoji(guessHint.hint);
            }
        }
    }
    return result;
}

async function copyHistoryResult() {
    try {
        const guessesCount = Object.keys(history.value).length;
        let result = getTitle(guessesCount);
        for (const guess of Object.values(history.value)) {
            result += `\n${formatGuess(guess)}`;
        }
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
