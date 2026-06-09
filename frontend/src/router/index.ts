import { Route } from '#/router/Route';
import { useAppStore } from '#/stores/appStore';
import { GAME_MODE_DISPLAY_NAMES } from '#/types/api/gameMode';
import AuthCallback from '#/views/AuthCallback.vue';
import TmnfTrialBlurGame from '#/views/games/blur/TmnfTrialBlurGame.vue';
import Tm2020RpgClassicGame from '#/views/games/classic/Tm2020RpgClassicGame.vue';
import Tm2020TrialClassicGame from '#/views/games/classic/Tm2020TrialClassicGame.vue';
import Tm2RpgClassicGame from '#/views/games/classic/Tm2RpgClassicGame.vue';
import Tm2TrialClassicGame from '#/views/games/classic/Tm2TrialClassicGame.vue';
import TmnfRpgClassicGame from '#/views/games/classic/TmnfRpgClassicGame.vue';
import TmnfTrialClassicGame from '#/views/games/classic/TmnfTrialClassicGame.vue';
import Tm2020RpgGeoguessrGame from '#/views/games/geoguessr/Tm2020RpgGeoguessrGame.vue';
import TmnfTrialZoomGame from '#/views/games/zoom/TmnfTrialZoomGame.vue';
import HomePage from '#/views/HomePage.vue';
import UserStats from '#/views/user/UserStats.vue';
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
                titleKey: GAME_MODE_DISPLAY_NAMES['CLASSIC_TMNF_TRIAL']
            }
        },
        {
            name: Route.TMNF_RPG_CLASSIC_MODE,
            path: Route.TMNF_RPG_CLASSIC_MODE,
            component: TmnfRpgClassicGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['CLASSIC_TMNF_RPG']
            }
        },
        {
            name: Route.TM2_TRIAL_CLASSIC_MODE,
            path: Route.TM2_TRIAL_CLASSIC_MODE,
            component: Tm2TrialClassicGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['CLASSIC_TM2_TRIAL']
            }
        },
        {
            name: Route.TM2_RPG_CLASSIC_MODE,
            path: Route.TM2_RPG_CLASSIC_MODE,
            component: Tm2RpgClassicGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['CLASSIC_TM2_RPG']
            }

        },
        {
            name: Route.TM2020_TRIAL_CLASSIC_MODE,
            path: Route.TM2020_TRIAL_CLASSIC_MODE,
            component: Tm2020TrialClassicGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['CLASSIC_TM2020_TRIAL']
            }

        },
        {
            name: Route.TM2020_RPG_CLASSIC_MODE,
            path: Route.TM2020_RPG_CLASSIC_MODE,
            component: Tm2020RpgClassicGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['CLASSIC_TM2020_RPG']
            }

        },
        {
            name: Route.TM2020_RPG_GEOGUESSR_MODE,
            path: Route.TM2020_RPG_GEOGUESSR_MODE,
            component: Tm2020RpgGeoguessrGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['GEOGUESSR_TM2020_RPG']
            }

        },
        {
            name: Route.TMNF_TRIAL_BLUR_MODE,
            path: Route.TMNF_TRIAL_BLUR_MODE,
            component: TmnfTrialBlurGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['BLUR_TMNF_TRIAL']
            }

        },
        {
            name: Route.TMNF_TRIAL_ZOOM_MODE,
            path: Route.TMNF_TRIAL_ZOOM_MODE,
            component: TmnfTrialZoomGame,
            meta: {
                titleKey: GAME_MODE_DISPLAY_NAMES['ZOOM_TMNF_TRIAL']
            }

        },
        {
            name: Route.AUTH_CALLBACK,
            path: Route.AUTH_CALLBACK,
            component: AuthCallback,
            meta: {
                titleKey: 'Auth Callback'
            }

        },
        {
            name: Route.USER_STATS,
            path: Route.USER_STATS,
            component: UserStats,
            meta: {
                titleKey: 'User Stats'
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

router.afterEach((to) => {
    document.title = to.meta.titleKey
        ? `TMMaple - ${to.meta.titleKey as string}`
        : 'TMMaple';
});

export default router;
