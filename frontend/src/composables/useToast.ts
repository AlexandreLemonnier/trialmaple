import { RequestError } from '#/classes/RequestError';
import { i18n } from '#/main';
import { useAppStore } from '#/stores/appStore';
import type { ToastVariant } from '#/types/ToastVariant';

function formatErrorDescription(description: string, error: unknown) {
    let res = `${description} ${i18n.global.t('toast.titles.error')}`;
    if (error instanceof RequestError) {
        res += ` : ${error.errorCode} (${error.statusCode})`;
    }
    return res;
}

type BaseParams = {
    variant: ToastVariant;
    title?: string;
    description: string;
};

type SingleRequestErrorParams = {
    description?: string;
    error: unknown;
};

type MultipleRequestErrorParams = {
    descriptionByError: Record<string, unknown>;
};

type Params = BaseParams | SingleRequestErrorParams | MultipleRequestErrorParams;

export function useToast(options: Params) {
    const { toast, showToast } = useAppStore();

    toast.id = (toast.id + 1) % 2;
    if ('variant' in options) {
        toast.variant = options.variant;
        toast.title = options?.title ?? '';
        toast.descriptions = [options.description];
        toast.autoClose = true;
    } else if ('error' in options) {
        toast.variant = 'error';
        toast.descriptions = [formatErrorDescription(options.description ?? '', options.error)];
        toast.autoClose = false;
    } else if ('descriptionByError' in options) {
        toast.variant = 'error';
        toast.descriptions = Object.entries(options.descriptionByError).map(
            ([description, error]) => formatErrorDescription(description, error)
        );
        toast.autoClose = false;
    }

    showToast();
}
