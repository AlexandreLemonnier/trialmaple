import { URL } from 'node:url';

import tailwindcss from '@tailwindcss/vite';
import vue from '@vitejs/plugin-vue';
import { defineConfig, loadEnv } from 'vite';
import type { UserConfig } from 'vite';
import vueDevTools from 'vite-plugin-vue-devtools';
import svgLoader from 'vite-svg-loader';

import { createVueViteConfig } from '../configs/vite/vue';

export default defineConfig(({ mode }: { mode: string }) => {
    return createVueViteConfig({
        appRootUrl: new URL('.', import.meta.url),
        mode,
        loadEnv,
        plugins: [
            vue(),
            vueDevTools(),
            tailwindcss(),
            svgLoader()
        ]
    }) as UserConfig;
});
