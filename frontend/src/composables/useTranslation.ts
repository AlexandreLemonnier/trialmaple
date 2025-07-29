import fr from '#/locales/fr.json';
import { useI18n } from 'vue-i18n';

export function useTranslation() {
    const { t } = useI18n<{
        message: typeof fr
    }, 'fr'>({
        inheritLocale: true,
        messages: { fr }
    });

    return { t };
}
