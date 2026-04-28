import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAppStore = defineStore('app', () => {
    const isFromOldDomainName = ref(false);

    function setIsFromOldDomainName(value: boolean) {
        isFromOldDomainName.value = value;
    }

    return { isFromOldDomainName, setIsFromOldDomainName };
});
