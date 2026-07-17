import { Route } from '#/router/Route';
import Login from '#/views/Login.vue';
import TmnfTrialMapsList from '#/views/TmnfTrialMapsList.vue';
import User from '#/views/User.vue';
import UsersList from '#/views/UsersList.vue';
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
            name: Route.USERS,
            path: Route.USERS,
            component: UsersList,
            meta: {
                titleKey: 'Users'
            }
        },
        {
            name: Route.USER_DETAIL,
            path: Route.USER_DETAIL,
            component: User,
            props: true,
            meta: {
                titleKey: 'User'
            }
        },
        {
            name: Route.TMNF_TRIAL_MAPS,
            path: Route.TMNF_TRIAL_MAPS,
            component: TmnfTrialMapsList,
            meta: {
                titleKey: 'TMNF Trial Maps'
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
