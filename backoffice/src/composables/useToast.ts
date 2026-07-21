import { RequestError } from '#/classes/RequestError';
import { useToast as usePrimeToast } from 'primevue/usetoast';

export function useToast() {
    const toast = usePrimeToast();

    function add(message: {
        summary: string
    } & ({
        severity: 'success' | 'info' | 'warn' | 'secondary' | 'contrast',
        detail: string
    } | {
        severity: 'error'
        error: unknown,
    })) {
        const isError = message.severity === 'error';
        let detail = '';
        if (isError) {
            detail = message.error instanceof RequestError ? message.error.errorMessage : 'unknown error';
        } else {
            detail = message.detail;
        }
        toast.add({
            severity: message.severity,
            summary: message.summary,
            detail,
            life: isError ? undefined : 3000
        });
    }

    return { add };
}
