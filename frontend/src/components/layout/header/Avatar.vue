<template>
    <div v-if="user" class="relative flex items-center" ref="menuRef">
        <button type="button"
                class="flex items-center focus:outline-none"
                @click="isMenuOpen = !isMenuOpen">
            <img :src="getDiscordAvatarUrl(user.discordId, user.avatar, user.discriminator)"
                 :alt="`${user.username} avatar`"
                 class="w-6 h-6 md:w-10 md:h-10 rounded-full border-2 border-discord object-cover shadow-sm cursor-pointer dark:brightness-[0.9]" />
        </button>

        <Transition name="menu">
            <div v-if="isMenuOpen" class="absolute right-0 top-full mt-2 flex flex-col min-w-38 p-2 text-nowrap bg-app-background rounded-md shadow-lg border border-app-border/30 z-50">
                <div class="absolute -top-1.5 right-1.5 md:right-3.5 w-3 h-3 rotate-45 bg-app-background border-t border-l border-app-border/30"></div>
                <span class="font-bold truncate px-2 py-1.5">
                    {{ user.username }}
                </span>
                <div class="w-full border-b border-app-border/20 my-1"></div>
                <span class="w-full rounded-md px-2 py-1.5 cursor-pointer hover:bg-selection-background" @click="() => {
                    closeMenu();
                    router.push({ name: Route.USER_STATS });
                }">My stats</span>
                <div class="w-full border-b border-app-border/20 mt-1 mb-2"></div>
                <LogoutButton />
            </div>
        </Transition>
    </div>
</template>

<script setup lang="ts">
import LogoutButton from '#/components/layout/header/LogoutButton.vue';
import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore.js';
import { getDiscordAvatarUrl } from '#/utils/discord';
import { onClickOutside } from '@vueuse/core';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const { user } = storeToRefs(useAppStore());
const router = useRouter();

const isMenuOpen = ref(false);
const menuRef = ref<HTMLDivElement | null>(null);

function closeMenu() {
    isMenuOpen.value = false;
}

onClickOutside(menuRef, () => {
    closeMenu();
});

</script>
