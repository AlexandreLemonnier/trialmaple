<template>
    <div class="flex flex-wrap items-center gap-2 font-semibold text-sm md:text-base">
        <span :title="tooltip" :class="tooltip ? 'cursor-help' : ''">{{ label }}</span>
        <div v-for="hintElement in hints"
             :key="getKey(hintElement.value)"
             class="flex items-center gap-2 rounded-full border border-app-border px-2.5 py-0.5"
             :class="{
                 'bg-error': hintElement.hint === 'LESS' || hintElement.hint === 'MORE' || hintElement.hint === false,
                 'bg-success': hintElement.hint === 'EQUAL' || hintElement.hint === true
             }">
            <div v-if="isWrHolder(hintElement.value)" v-html="displayWrHolderHtml(hintElement.value)"></div>
            <span v-else-if="typeof hintElement.value === 'boolean'">{{ displayBoolean(hintElement.value) }}</span>
            <span v-else>{{ hintElement.value }}</span>
            <Icon v-if="hintElement.hint === 'LESS'" name="chevron-down" size="sm" />
            <Icon v-else-if="hintElement.hint === 'MORE'" name="chevron-up" size="sm" />
        </div>
    </div>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import type { DeltaHint } from '#/types/api/deltaHint';
import type { HintPair, WrHolder } from '#/types/api/guess';

type HintValue = string | number | boolean | WrHolder;

defineProps<{
    label: string;
    tooltip?: string;
    hints: HintPair<HintValue, boolean | DeltaHint>[];
}>();

function isWrHolder(value: HintValue): value is WrHolder {
    return typeof value === 'object' && value !== null && 'login' in value;
}

function displayWrHolderHtml(value: WrHolder) {
    const displayNameHtml = MPStyle.Parser.toHTML(value.displayName);
    const login = value.login;
    return `${displayNameHtml} <span>(${login})</span>`;
}

function displayBoolean(value: boolean) {
    return value ? 'Yes' : 'No';
}

function getKey(value: HintValue) {
    if (isWrHolder(value)) {
        return value.login;
    } else if (typeof value === 'boolean') {
        return displayBoolean(value);
    }
    return value;
}
</script>
