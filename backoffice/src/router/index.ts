import { Route } from '#/router/Route';
import Login from '#/views/Login.vue';
import Tm2020RpgMapsList from '#/views/Tm2020RpgMapsList.vue';
import Tm2020TrialMapsList from '#/views/Tm2020TrialMapsList.vue';
import Tm2RpgMapsList from '#/views/Tm2RpgMapsList.vue';
import Tm2TrialMapsList from '#/views/Tm2TrialMapsList.vue';
import TmnfRpgMapsList from '#/views/TmnfRpgMapsList.vue';
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
            name: Route.TMNF_RPG_MAPS,
            path: Route.TMNF_RPG_MAPS,
            component: TmnfRpgMapsList,
            meta: {
                titleKey: 'TMNF RPG Maps'
            }
        },
        {
            name: Route.TM2_TRIAL_MAPS,
            path: Route.TM2_TRIAL_MAPS,
            component: Tm2TrialMapsList,
            meta: {
                titleKey: 'TM2 Trial Maps'
            }
        },
        {
            name: Route.TM2_RPG_MAPS,
            path: Route.TM2_RPG_MAPS,
            component: Tm2RpgMapsList,
            meta: {
                titleKey: 'TM2 RPG Maps'
            }
        },
        {
            name: Route.TM2020_TRIAL_MAPS,
            path: Route.TM2020_TRIAL_MAPS,
            component: Tm2020TrialMapsList,
            meta: {
                titleKey: 'TM2020 Trial Maps'
            }
        },
        {
            name: Route.TM2020_RPG_MAPS,
            path: Route.TM2020_RPG_MAPS,
            component: Tm2020RpgMapsList,
            meta: {
                titleKey: 'TM2020 RPG Maps'
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
