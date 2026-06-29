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
import { useShareStore } from '#/stores/shareStore';
import { GAME_MODE_DISPLAY_NAMES, type GameMode } from '#/types/api/gameMode';
import { gamesInfo } from '#/types/GameInfo';
import { copyToClipboard } from '#/utils/copyToClipboard';
import { ref } from 'vue';

const { gameMode: gameModeProp, shareAll } = defineProps<{
    gameMode: GameMode;
    shareAll?: boolean;
}>();

const { getFormattedShareString } = useShareStore();

type CopyStatus = 'NONE' | 'SUCCESS' | 'ERROR';

const resultCopyStatus = ref<CopyStatus>('NONE');

const resultCopyMessage: Record<CopyStatus, string> = {
    SUCCESS: 'Copied!',
    ERROR: 'Error',
    NONE: shareAll ? 'Share all' : 'Share result'
};

const resultCopyClass: Record<CopyStatus, string> = {
    SUCCESS: 'text-confirmation-text italic',
    ERROR: 'text-error italic',
    NONE: ''
};

function getTitle(gameMode: GameMode, guessesCount: number, hasWon: boolean, dailyMapNumber: number) {
    return `${GAME_MODE_DISPLAY_NAMES[gameMode]} #${dailyMapNumber} - ${guessesCount} ${guessesCount <= 1 ? 'guess' : 'guesses'} ${hasWon ? '😼👍' : '😿❌'}`;
}

function copySingleHistoryResult(gameMode: GameMode) {
    const gameStore = createGameStore(gameMode)();
    const { history, dailyMapNumber, historyContainsSuccess } = gameStore;
    if (!dailyMapNumber) return '';

    const guessesCount = Object.keys(history).length;
    const hasWon = historyContainsSuccess();
    const formattedResult = getFormattedShareString(gameMode);
    return formattedResult ? getTitle(gameMode, guessesCount, hasWon, dailyMapNumber) + formattedResult : '';
}

function copyAllHistoryResults() {
    let result = '';
    const visitedGameModes: Set<GameMode> = new Set();
    let currentGameMode = gameModeProp;
    let isFirst = true;
    // Loop over all linked game modes (same TM game, same category)
    while (!visitedGameModes.has(currentGameMode)) {
        // Build share string for the current game mode
        visitedGameModes.add(currentGameMode);
        const currentGameModeShareString = copySingleHistoryResult(currentGameMode);
        if (currentGameModeShareString) {
            result += (isFirst ? '' : '\n') + currentGameModeShareString;
        }
        isFirst = false;

        // Go to next game mode
        const nextGameMode = gamesInfo[currentGameMode].nextGameMode;
        if (nextGameMode) {
            currentGameMode = nextGameMode;
        } else {
            break;
        }
    }
    return result;
}

async function copyHistoryResult() {
    try {
        const result = shareAll ? copyAllHistoryResults() : copySingleHistoryResult(gameModeProp);
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
