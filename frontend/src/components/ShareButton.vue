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
import { useAppStore } from '#/stores/appStore';
import type { DeltaHint } from '#/types/api/deltaHint';
import { copyToClipboard } from '#/utils/copyToClipboard';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const appStore = useAppStore();
const { history, dailyMapNumber } = storeToRefs(appStore);

type CopyStatus = 'NONE' | 'SUCCESS' | 'ERROR';

const resultCopyStatus = ref<CopyStatus>('NONE');

const resultCopyMessage: Record<CopyStatus, String> = {
    SUCCESS: 'Copied!',
    ERROR: 'Error',
    NONE: 'Share result'
};

const resultCopyClass: Record<CopyStatus, String> = {
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

async function copyHistoryResult() {
    const guessesCount = Object.keys(history.value).length;
    let result = dailyMapNumber.value ? `I solved TrialMaple #${dailyMapNumber.value} in ${guessesCount} ${guessesCount <= 1 ? 'guess' : 'guesses'} 😼👍` : '';
    for (const guess of Object.values(history.value)) {
        const difficultyEmoji = hintToEmoji(guess.difficulty.hint);
        const pointEmoji = hintToEmoji(guess.points.hint);
        const checkpointEmoji = hintToEmoji(guess.checkpoints.hint);
        const finisherCountEmoji = hintToEmoji(guess.finisherCount.hint);
        const worldRecordEmoji = hintToEmoji(guess.worldRecord.hint);
        const authorsEmoji = hintToEmoji(guess.authors.some((hintPair) => hintPair.hint));
        result += `\n${difficultyEmoji}${pointEmoji}${checkpointEmoji}${finisherCountEmoji}${worldRecordEmoji}${authorsEmoji}`;
    }
    resultCopyStatus.value = await copyToClipboard(result) ? 'SUCCESS' : 'ERROR';
    setTimeout(() => {
        resultCopyStatus.value = 'NONE';
    }, 1500);
}
</script>
