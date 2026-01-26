import { useStorage } from '@vueuse/core';
import { onMounted } from 'vue';

export function useTheme() {
    const theme = useStorage<'light' | 'dark'>('theme', 'light');

    function applyTheme() {
        const root = document.documentElement;
        if (theme.value === 'dark') {
            root.classList.add('dark');
        } else {
            root.classList.remove('dark');
        }
    }

    function toggleTheme() {
        theme.value = theme.value === 'light' ? 'dark' : 'light';
        applyTheme();
    }

    onMounted(() => {
        if (!theme.value) {
            // Detect system theme
            theme.value = globalThis.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
        }
        applyTheme();
    });

    return { theme, toggleTheme };
}
