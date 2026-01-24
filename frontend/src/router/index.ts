import { Route } from '#/router/Route';
import Tm2RpgClassicGame from '#/views/Tm2RpgClassicGame.vue';
import Tm2TrialClassicGame from '#/views/Tm2TrialClassicGame.vue';
import TmnfRpgClassicGame from '#/views/TmnfRpgClassicGame.vue';
import TmnfTrialClassicGame from '#/views/TmnfTrialClassicGame.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
    history: createWebHistory('/'),
    routes: [
        {
            name: Route.TMNF_TRIAL_CLASSIC_MODE,
            path: Route.TMNF_TRIAL_CLASSIC_MODE,
            component: TmnfTrialClassicGame,
            alias: '/',
            meta: {
                titleKey: 'TMNF Trial'
            }
        },
        {
            name: Route.TMNF_RPG_CLASSIC_MODE,
            path: Route.TMNF_RPG_CLASSIC_MODE,
            component: TmnfRpgClassicGame,
            meta: {
                titleKey: 'TMNF RPG'
            }
        },
        {
            name: Route.TM2_TRIAL_CLASSIC_MODE,
            path: Route.TM2_TRIAL_CLASSIC_MODE,
            component: Tm2TrialClassicGame,
            meta: {
                titleKey: 'TM2 Trial'
            }
        },
        {
            name: Route.TM2_RPG_CLASSIC_MODE,
            path: Route.TM2_RPG_CLASSIC_MODE,
            component: Tm2RpgClassicGame,
            meta: {
                titleKey: 'TM2 RPG'
            }

        },
        {
            path: '/:pathMatch(.*)', redirect: '/'
        }
    ]
});

router.beforeEach(async (toString, from, next) => {
    return next();
});

export default router;
