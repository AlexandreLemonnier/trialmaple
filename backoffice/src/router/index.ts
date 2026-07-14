import { Route } from '#/router/Route';
import Dashboard from '#/views/Dashboard.vue';
import Login from '#/views/Login.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
    history: createWebHistory('/'),
    routes: [
        {
            name: Route.LOGIN,
            path: Route.LOGIN,
            component: Login,
            meta: {
                titleKey: 'Login'
            }
        },
        {
            name: Route.DASHBOARD,
            path: Route.DASHBOARD,
            component: Dashboard,
            meta: {
                titleKey: 'Dashboard'
            }
        },
        {
            path: '/:pathMatch(.*)', redirect: Route.LOGIN
        }
    ]
});


router.afterEach((to) => {
    document.title = to.meta.titleKey
        ? `TMMaple Backoffice - ${to.meta.titleKey as string}`
        : 'TmMaple Backoffice';
});

export default router;
