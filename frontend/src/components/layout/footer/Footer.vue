<template>
    <footer class="w-full bg-footer-background border-t border-app-border/20 text-app-text-muted py-2 lg:py-4 px-4 mt-auto">
        <div class="max-w-7xl mx-auto flex flex-col md:flex-row justify-between items-center gap-3 lg:gap-6">

            <div class="flex flex-col text-center md:text-left max-w-md text-sm font-semibold">
                <span>© 2026 tmmaple.com</span>
                <span class="text-xs font-normal text-gray-500">Made by Arsolight</span>
            </div>

            <div class="flex flex-wrap justify-center gap-x-6 gap-y-2 text-xs font-medium">
                <RouterLink :to="{ name: Route.PRIVACY_POLICY }" class="hover:text-app-text-primary">Privacy Policy</RouterLink>
                <RouterLink :to="{ name: Route.TERMS_OF_SERVICE }" class="hover:text-app-text-primary">Terms of Service</RouterLink>
                <span class="text-gray-600">|</span>
                <RouterLink :to="{ name: Route.HOME }" class="hover:text-app-text-primary">Home</RouterLink>
            </div>

            <span class="block md:hidden text-sm font-semibold hover:text-app-text-primary underline cursor-pointer" @click="isDiscordModalOpen = true">Discord</span>
            <Button class="hidden md:flex bg-discord/70 text-primary-white"
                    label="Discord"
                    icon-name="discord"
                    icon-size="md"
                    icon-position="right"
                    @click="isDiscordModalOpen = true" />
        </div>
    </footer>
    <DiscordModal v-model="isDiscordModalOpen" />
</template>

<script setup lang="ts">
import Button from '#/components/Button.vue';
import DiscordModal from '#/components/modal/DiscordModal.vue';
import { Route } from '#/router/Route';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const isDiscordModalOpen = ref(false);

</script>
