// composables/useScrollbarDetection.ts
import type { Ref } from 'vue';
import { onBeforeUnmount, ref, watch } from 'vue';

export function useScrollbarDetection(targetRef: Ref<HTMLElement | undefined>) {
    const hasVerticalScrollbar = ref(false);
    const hasHorizontalScrollbar = ref(false);

    let observer: ResizeObserver | null = null;

    const checkScrollbars = () => {
        const el = targetRef.value;
        if (!el) return;

        hasVerticalScrollbar.value = el.scrollHeight > el.clientHeight;
        hasHorizontalScrollbar.value = el.scrollWidth > el.clientWidth;
    };

    watch(targetRef, (el) => {
        if (el) {
            observer?.disconnect();
            observer = new ResizeObserver(checkScrollbars);
            observer.observe(el);
            checkScrollbars();
        }
    }, {
        immediate: true
    });

    onBeforeUnmount(() => {
        observer?.disconnect();
    });

    return {
        hasVerticalScrollbar,
        hasHorizontalScrollbar
    };
}
