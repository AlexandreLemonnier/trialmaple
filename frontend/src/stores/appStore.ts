import type { User } from '#/types/api/user';
import { defineStore } from 'pinia';
import { computed, ref } from 'vue';

export const useAppStore = defineStore('app', () => {
    const isFromOldDomainName = ref(false);

    function setIsFromOldDomainName(value: boolean) {
        isFromOldDomainName.value = value;
    }

    const user = ref<User | null>(null);
    const isConnected = computed(() => user.value !== null);

    return { isFromOldDomainName, setIsFromOldDomainName, user, isConnected };
});
