<template>
    <div class="flex items-center gap-2 font-semibold text-sm md:text-base">
        <span>{{ label }}</span>
        <div v-for="hintElement in hints"
             :key="hintElement.value"
             class="flex items-center gap-2 rounded-full border px-2.5 py-0.5"
             :class="{
                 'bg-error': hintElement.hint === 'LESS' || hintElement.hint === 'MORE' || hintElement.hint === false,
                 'bg-success': hintElement.hint === 'EQUAL' || hintElement.hint === true
             }">
            <span>{{ hintElement.value }}</span>
            <Icon v-if="hintElement.hint === 'LESS'" name="chevron-down" size="sm" />
            <Icon v-else-if="hintElement.hint === 'MORE'" name="chevron-up" size="sm" />
        </div>
    </div>
</template>

<script setup lang="ts">
import type { DeltaHint } from '#/types/api/deltaHint';
import type { HintPair } from '#/types/api/guess';
import Icon from './Icon.vue';

defineProps<{
    label: string;
    hints: HintPair<string | number, boolean | DeltaHint>[];
}>();
</script>
