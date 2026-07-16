import tailwindcss from '@tailwindcss/vite';
import vue from '@vitejs/plugin-vue';
import type { UserConfig } from 'vite';
import { defineConfig, loadEnv } from 'vite';

import { createVueViteConfig } from '../configs/vite/vue';

export default defineConfig(({ mode }: { mode: string }) => {
    return createVueViteConfig({
        appRootUrl: new URL('.', import.meta.url),
        mode,
        loadEnv,
        plugins: [
            vue(),
            tailwindcss()
        ]
    }) as UserConfig;
});
