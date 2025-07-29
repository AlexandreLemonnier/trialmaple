import { nextTick } from 'vue';

export async function useFocus(element?: HTMLElement | null) {
    await nextTick(() => {
        element?.focus();

        if (element instanceof HTMLInputElement) {
            const length = element?.value?.length;
            element?.setSelectionRange(length, length);
        }
    });
}
