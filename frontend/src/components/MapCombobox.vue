<template>
    <div ref="dropdownRef" class="relative w-full flex-1 min-w-0">
        <!-- Combobox input -->
        <div class="flex w-full justify-between items-center text-lg lg:text-xl xl:text-2xl rounded-full border-2 border-app-border py-2 px-4" @click="open">
            <input ref="inputRef"
                   v-model="search"
                   class="flex-1 min-w-0 bg-transparent outline-none truncate cursor-text"
                   :placeholder="selectedMap ? undefined : 'Select a map...'"
                   @keydown="onKeyDown" />

            <Icon class="cursor-pointer" :name="isOpen ? 'chevron-up' : 'chevron-down'" size="sm" />
        </div>

        <!-- Dropdown -->
        <ul v-if="isOpen" class="absolute text-md lg:text-lg xl:text-xl mt-2 w-full max-h-60 overflow-y-auto rounded-xl border border-app-border shadow-lg bg-app-background scrollbar-hide z-20">
            <li v-for="(map, index) in filteredMaps"
                :key="map.uuid"
                ref="itemRefs"
                @click="select(map)"
                class="px-4 py-1 cursor-pointer"
                :class="{ 'bg-neutral-400/20': index === highlightedIndex }">
                {{ map.name }}
            </li>

            <li v-if="filteredMaps.length === 0" class="px-4 py-2 text-sm">No maps found.</li>
        </ul>
    </div>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import type { TmMap } from '#/types/api/tmMap';
import { computed, nextTick, onMounted, onUnmounted, ref, watch } from 'vue';

const { maps, pickedMaps } = defineProps<{
    maps: TmMap[];
    pickedMaps: TmMap[];
}>();

const selectedMap = defineModel<TmMap>();
const remainingMaps = computed(() => maps.filter((map) => !pickedMaps.includes(map)));

const dropdownRef = ref<HTMLElement | null>(null);
const inputRef = ref<HTMLInputElement | null>(null);
const itemRefs = ref<(HTMLElement | null)[]>([]);

const isOpen = ref(false);
const search = ref('');
const highlightedIndex = ref(-1);
const previousValue = ref<string>();

/* Filtered maps */
const filteredMaps = computed(() => {
    const filter = search.value.toLowerCase();
    if (!filter) return remainingMaps.value;
    return remainingMaps.value.filter((map) => map.name.toLowerCase().includes(filter));
});

/* Open dropdown */
function open() {
    if (isOpen.value) return;

    previousValue.value = selectedMap.value?.name;

    search.value = '';
    isOpen.value = true;
    highlightedIndex.value = -1;

    nextTick(() => inputRef.value?.focus());
}

/* Close dropdown */
function close(committed = false) {
    isOpen.value = false;
    highlightedIndex.value = -1;
    itemRefs.value = [];

    if (!committed) {
        search.value = previousValue.value ?? '';
    }
}

/* Select item */
function select(map: TmMap) {
    selectedMap.value = map;
    search.value = map.name;
    close(true);
}

/* Scroll highlighted item into view */
function scrollToHighlighted() {
    const el = itemRefs.value[highlightedIndex.value];
    el?.scrollIntoView({ block: 'nearest' });
}

/* Keyboard navigation */
function onKeyDown(e: KeyboardEvent) {
    if (!isOpen.value) return;

    switch (e.key) {
        case 'ArrowDown':
            e.preventDefault();
            highlightedIndex.value = (highlightedIndex.value + 1) % filteredMaps.value.length;
            scrollToHighlighted();
            break;

        case 'ArrowUp':
            e.preventDefault();
            highlightedIndex.value = highlightedIndex.value <= 0 ? filteredMaps.value.length - 1 : highlightedIndex.value - 1;
            scrollToHighlighted();
            break;

        case 'Enter':
            e.preventDefault();
            if (highlightedIndex.value >= 0) {
                select(filteredMaps.value[highlightedIndex.value]!);
            }
            break;

        case 'Escape':
            e.preventDefault();
            close(false);
            break;
    }
}

/* Close on outside click */
function handleClickOutside(event: MouseEvent) {
    if (isOpen.value && !dropdownRef.value?.contains(event.target as Node)) {
        close(false);
    }
}

onMounted(() => {
    document.addEventListener('mousedown', handleClickOutside);
});

onUnmounted(() => {
    document.removeEventListener('mousedown', handleClickOutside);
});

/* Keep search synced with selection */
watch(selectedMap, () => {
    if (!isOpen.value) {
        search.value = selectedMap.value?.name ?? '';
    }
});
</script>
