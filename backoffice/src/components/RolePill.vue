<template>
    <span class="px-2.5 py-1 text-sm font-semibold rounded-full"
          :class="{
              'bg-blue-100 text-blue-700': userType === 'USER',
              'bg-purple-100 text-purple-700': userType === 'ADMIN'
          }">
        {{ userType }}
    </span>
</template>

<script setup lang="ts">
import type { UserType } from '#/types/api/user';
defineProps<{
    userType: UserType;
}>();
</script>
