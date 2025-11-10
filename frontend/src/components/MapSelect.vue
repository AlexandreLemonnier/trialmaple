<template>
  <div class="relative w-full flex-1 min-w-0">
    <button
      @click="isOpen = !isOpen"
      class="flex w-full justify-between items-center text-lg lg:text-2xl rounded-full border-2 py-2 px-4"
    >
      <span class="truncate">{{ selectedMap?.name || "Select a map..." }}</span>
      <Icon :name="isOpen ? 'chevron-up' : 'chevron-down'" size="sm" />
    </button>

    <ul
      v-if="isOpen"
      class="absolute text-md lg:text-lg mt-2 w-full max-h-60 overflow-y-auto rounded-xl border shadow-lg scrollbar-hide"
    >
      <li
        v-for="map in maps"
        :key="map.name"
        @click="select(map)"
        class="px-4 py-1 hover:bg-neutral-400/20 cursor-pointer"
      >
        {{ map.name }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import Icon from '#/components/Icon.vue';
import { TrialMap } from '#/types/api/trialMap';
import { ref } from 'vue';

defineProps<{
    maps: TrialMap[];
}>();

const isOpen = ref(false);
const selectedMap = defineModel<TrialMap>();

function select(map: TrialMap) {
  selectedMap.value = map;
  isOpen.value = false;
}
</script>