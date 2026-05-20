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
        <div v-if="isOpen" class="absolute mt-2 w-full rounded-xl border border-app-border shadow-lg bg-app-background overflow-hidden z-20">
            <ul class="text-md lg:text-lg xl:text-xl max-h-60 overflow-y-auto">
                <li v-for="(map, index) in filteredMaps"
                    :key="String(map[uniqueId])"
                    ref="itemRefs"
                    @click="select(map)"
                    class="px-4 py-1 cursor-pointer hover:bg-selection-background"
                    :class="{ 'bg-selection-background': map[uniqueId] === selectedMap?.[uniqueId] || index === highlightedIndex }">
                    {{ map[nameProp] }}
                </li>

                <li v-if="filteredMaps.length === 0" class="px-4 py-2 text-sm">No maps found.</li>
            </ul>
        </div>
    </div>
</template>

<script setup lang="ts" generic="T">
import Icon from '#/components/Icon.vue';
import { deburr } from 'moderndash';
import { computed, nextTick, onMounted, onUnmounted, ref, watch } from 'vue';

const { maps, pickedMaps, nameProp, uniqueId } = defineProps<{
    maps: T[];
    pickedMaps: T[];
    nameProp: keyof T;
    uniqueId: keyof T;
}>();

const selectedMap = defineModel<T>();
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
    return remainingMaps.value.filter((map) => deburr(String(map[nameProp])).toLowerCase().includes(deburr(filter)));
});

/* Scroll highlighted item into view */
function scrollToHighlighted() {
    const el = itemRefs.value[highlightedIndex.value];
    el?.scrollIntoView({ block: 'center' });
}

/* Open dropdown */
function open() {
    if (isOpen.value) return;

    previousValue.value = String(selectedMap.value?.[nameProp] ?? '');

    search.value = '';
    isOpen.value = true;

    // Find selected map index
    if (selectedMap.value) {
        highlightedIndex.value = filteredMaps.value.findIndex(
            (map) => map[uniqueId] === selectedMap.value?.[uniqueId]
        );
    } else {
        highlightedIndex.value = -1;
    }

    // Focus and scroll to selection
    nextTick(() => {
        inputRef.value?.focus();
        if (highlightedIndex.value >= 0) {
            scrollToHighlighted();
        }
    });
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
function select(map: T) {
    selectedMap.value = map;
    search.value = String(map[nameProp]);
    close(true);
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
        search.value = String(selectedMap.value?.[nameProp] ?? '');
    }
});
</script>
