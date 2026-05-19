import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import HomePage from '#/views/HomePage.vue';
import Tm2020RpgClassicGame from '#/views/Tm2020RpgClassicGame.vue';
import Tm2020RpgGeoguessrGame from '#/views/Tm2020RpgGeoguessrGame.vue';
import Tm2020TrialClassicGame from '#/views/Tm2020TrialClassicGame.vue';
import Tm2RpgClassicGame from '#/views/Tm2RpgClassicGame.vue';
import Tm2TrialClassicGame from '#/views/Tm2TrialClassicGame.vue';
import TmnfRpgClassicGame from '#/views/TmnfRpgClassicGame.vue';
import TmnfTrialClassicGame from '#/views/TmnfTrialClassicGame.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
    history: createWebHistory('/'),
    routes: [
        {
            name: Route.HOME,
            path: Route.HOME,
            component: HomePage,
            meta: {
                titleKey: 'Home'
            }
        },
        {
            name: Route.TMNF_TRIAL_CLASSIC_MODE,
            path: Route.TMNF_TRIAL_CLASSIC_MODE,
            component: TmnfTrialClassicGame,
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
            name: Route.TM2020_TRIAL_CLASSIC_MODE,
            path: Route.TM2020_TRIAL_CLASSIC_MODE,
            component: Tm2020TrialClassicGame,
            meta: {
                titleKey: 'TM2020 Trial'
            }

        },
        {
            name: Route.TM2020_RPG_CLASSIC_MODE,
            path: Route.TM2020_RPG_CLASSIC_MODE,
            component: Tm2020RpgClassicGame,
            meta: {
                titleKey: 'TM2020 RPG'
            }

        },
        {
            name: Route.TM2020_RPG_GEOGUESSR_MODE,
            path: Route.TM2020_RPG_GEOGUESSR_MODE,
            component: Tm2020RpgGeoguessrGame,
            meta: {
                titleKey: 'TM2020 RPG Geoguessr'
            }

        },
        {
            path: '/:pathMatch(.*)', redirect: '/'
        }
    ]
});

router.beforeEach((to) => {
    const { setIsFromOldDomainName } = useAppStore();

    const isFromOldDomainName = to.query.isFromOldDomainName === 'true';
    setIsFromOldDomainName(isFromOldDomainName);

    return true;
});

export default router;
