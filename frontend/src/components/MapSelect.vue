<template>
  <div ref="dropdownRef" class="relative w-full flex-1 min-w-0" @keydown="onKeyDown">
    <button
      @click="isOpen = !isOpen"
      class="flex w-full justify-between items-center text-lg lg:text-2xl rounded-full border-2 py-2 px-4 cursor-pointer"
    >
      <span class="truncate">{{ selectedMap || "Select a map..." }}</span>
      <Icon :name="isOpen ? 'chevron-up' : 'chevron-down'" size="sm" />
    </button>

    <ul
      v-if="isOpen"
      class="absolute text-md lg:text-lg mt-2 w-full max-h-60 overflow-y-auto rounded-xl border shadow-lg bg-white scrollbar-hide z-20"
    >
    <li
      v-for="mapName in mapNames"
      :key="mapName"
      @click="select(mapName)"
      class="px-4 py-1 hover:bg-neutral-400/20 cursor-pointer"
      ref="itemRefs"
    >
      {{ mapName }}
    </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import { onMounted, onUnmounted, ref } from 'vue';

const { mapNames } = defineProps<{
    mapNames: string[];
}>();

const isOpen = ref(false);
const selectedMap = defineModel<string>();
const dropdownRef = ref<HTMLElement | null>(null);
const itemRefs = ref<(HTMLElement | null)[]>([]);

function select(mapName: string) {
  selectedMap.value = mapName;
  isOpen.value = false;
}

/* Scroll to list item */
function scrollToItem(index: number) {
  const ul = dropdownRef.value?.querySelector('ul');
  const li = itemRefs.value[index];
  if (!ul || !li) return;

  // Relative position to the list
  const offsetTop = li.offsetTop;
  ul.scrollTop = offsetTop;
}

/* Find first map starting with pressed key + scroll */
function onKeyDown(e: KeyboardEvent) {
  if (e.key.length !== 1 || !/[a-z]/i.test(e.key)) return;

  const letter = e.key.toLowerCase();
  const index = mapNames.findIndex(name =>
    name.toLowerCase().startsWith(letter)
  );

  if (index !== -1) {
    scrollToItem(index);
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
});

/* Close select list when clicking outside */
function handleClickOutside(event: MouseEvent) {
  if (!dropdownRef.value) return;

  if (!dropdownRef.value.contains(event.target as Node)) {
    isOpen.value = false;
  }
}

</script>