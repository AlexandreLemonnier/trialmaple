import { Route } from '#/router/Route';
import { usePreferencesStore } from '#/stores/preferencesStore';
import Tm2RpgClassicGame from '#/views/Tm2RpgClassicGame.vue';
import Tm2TrialClassicGame from '#/views/Tm2TrialClassicGame.vue';
import TmnfRpgClassicGame from '#/views/TmnfRpgClassicGame.vue';
import TmnfTrialClassicGame from '#/views/TmnfTrialClassicGame.vue';
import { createRouter, createWebHistory } from 'vue-router';

function isValidRoute(route: Route) {
    return Object.values(Route).includes(route);
}

const router = createRouter({
    history: createWebHistory('/'),
    routes: [
        {
            path: '/',
            redirect: () => {
                const { favoritePage } = usePreferencesStore();
                // Safeguard if someone changes the value in local storage, or if a route gets deleted
                if (isValidRoute(favoritePage)) {
                    return favoritePage;
                }
                return Route.TMNF_TRIAL_CLASSIC_MODE;
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
            path: '/:pathMatch(.*)', redirect: '/'
        }
    ]
});

export default router;
