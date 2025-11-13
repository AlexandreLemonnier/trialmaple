<template>
  <div class="relative w-full flex-1 min-w-0">
    <button
      @click="isOpen = !isOpen"
      class="flex w-full justify-between items-center text-lg lg:text-2xl rounded-full border-2 py-2 px-4"
    >
      <span class="truncate">{{ selectedMap || "Select a map..." }}</span>
      <Icon :name="isOpen ? 'chevron-up' : 'chevron-down'" size="sm" />
    </button>

    <ul
      v-if="isOpen"
      class="absolute text-md lg:text-lg mt-2 w-full max-h-60 overflow-y-auto rounded-xl border shadow-lg bg-white scrollbar-hide"
    >
      <li
        v-for="mapName in mapNames"
        :key="mapName"
        @click="select(mapName)"
        class="px-4 py-1 hover:bg-neutral-400/20 cursor-pointer"
      >
        {{ mapName }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import { ref } from 'vue';

defineProps<{
    mapNames: string[];
}>();

const isOpen = ref(false);
const selectedMap = defineModel<string>();

function select(mapName: string) {
  selectedMap.value = mapName;
  isOpen.value = false;
}
</script>