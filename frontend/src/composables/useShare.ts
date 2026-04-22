import type { DeltaHint } from '#/types/api/deltaHint';

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

export function useShare() {
    return { hintToEmoji };
}
