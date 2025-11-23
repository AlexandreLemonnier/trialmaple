<template>
    <svg class="select-none inline-flex shrink-0" :class="[sizeToClass(size)]" stroke-width="2">
        <use :href="`#${name}`" />
    </svg>
</template>

<script setup lang="ts">
import spriteContent from '#/assets/icons.svg?raw';
import type { IconName } from '#/types/IconName';
import type { Size } from '#/types/Size';
import { onMounted } from 'vue';

defineProps<{
    name: IconName;
    size: Size;
}>();

onMounted(() => {
    const injectSprite = (content: string) => {
        const spriteId = 'svg-sprite';
        const existingSprite = document.getElementById(spriteId);

        if (existingSprite) {
            existingSprite.innerHTML = content;
        } else {
            const div = document.createElement('div');
            div.id = spriteId;
            div.style.display = 'none';
            div.inert = true;
            div.innerHTML = content;
            document.body.appendChild(div);
        }
    };

    injectSprite(spriteContent);

    if (import.meta.hot) {
        import.meta.hot.accept('#/assets/icons.svg?raw', (newModule) => {
            if (newModule) {
                injectSprite(newModule.default);
            }
        });
    }
});

function sizeToClass(size: Size) {
    return size === 'sm' ? 'size-5' : 'size-8';
}
</script>
