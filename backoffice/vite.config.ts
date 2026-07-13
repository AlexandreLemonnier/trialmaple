import vue from '@vitejs/plugin-vue';
import { defineConfig, loadEnv } from 'vite';
import type { UserConfig } from 'vite';

import { createVueViteConfig } from '../configs/vite/vue';

export default defineConfig(({ mode }: { mode: string }) => {
    return createVueViteConfig({
        appRootUrl: new URL('.', import.meta.url),
        mode,
        loadEnv,
        plugins: [
            vue()
        ]
    }) as UserConfig;
});
