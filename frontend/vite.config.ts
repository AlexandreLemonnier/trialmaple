import { URL } from 'node:url';

import tailwindcss from '@tailwindcss/vite';
import vue from '@vitejs/plugin-vue';
import { defineConfig, loadEnv } from 'vite';
import vueDevTools from 'vite-plugin-vue-devtools';
import svgLoader from 'vite-svg-loader';

export default ({ mode }: { mode: string }) => {
    process.env = { ...process.env, ...loadEnv(mode, process.cwd()) };

    const {
        VITE_APP_PORT,
        VITE_PROXIED_API_URL_PREFIX,
        VITE_API_URL
    } = process.env;

    return defineConfig({
        plugins: [
            vue(),
            vueDevTools(),
            tailwindcss(),
            svgLoader()
        ],
        resolve: {
            alias: {
                '#': new URL('src', import.meta.url).pathname
            }
        },
        server: {
            port: parseInt(VITE_APP_PORT as string),
            open: true,
            strictPort: true,
            proxy: {
                [VITE_PROXIED_API_URL_PREFIX as string]: {
                    target: VITE_API_URL,
                    rewrite: (path) => path.replace(VITE_PROXIED_API_URL_PREFIX as string, ''),
                    changeOrigin: true
                }
            }
        }
    });
};
