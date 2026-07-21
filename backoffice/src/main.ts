import '#/style.css';
import 'primeicons/primeicons.css';

import { createPinia } from 'pinia';
import { createApp } from 'vue';

import App from '#/App.vue';
import router from '#/router';

import Aura from '@primeuix/themes/aura';
import { ToastService } from 'primevue';
import PrimeVue from 'primevue/config';

const app = createApp(App);

app.use(createPinia());
app.use(ToastService);
app.use(router);
app.use(PrimeVue, {
    theme: {
        preset: Aura,
        options: {
            // Allow using Tailwind dark theme
            darkModeSelector: '.dark',
            // Handle CSS priority for Tailwind
            cssLayer: {
                name: 'primevue',
                order: 'base, primevue, utilities'
            }
        }
    }
});

app.mount('#app');
