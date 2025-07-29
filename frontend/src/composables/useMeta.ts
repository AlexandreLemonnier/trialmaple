import { useTranslation } from '#/composables/useTranslation';
import { computed, watch } from 'vue';
import { useRoute } from 'vue-router';

export function useMeta() {
    const { t } = useTranslation();
    const route = useRoute();

    const pageTitle = computed(() => {
        if (route?.meta?.titleKey) return t(`titles.${route.meta.titleKey as string}`);
        return 'Eridan';
    });

    function setPageTitle(value: string) {
        document.title = value;
    }

    watch(pageTitle, () => {
        setPageTitle(pageTitle.value);
    });

    setPageTitle(pageTitle.value);
}
