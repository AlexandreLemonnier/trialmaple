import type { Guess } from '#/types/api/guess';

const localStorageTypeMap = {
    history: {} as Record<string, Guess>
};
type LocalStorageData = typeof localStorageTypeMap;
type LocalStorageKeys = keyof LocalStorageData;

function getLocalStorageData() {
    const map = new Map();
    for (const keyName of Object.keys(localStorageTypeMap)) {
        try {
            const value = JSON.parse(localStorage.getItem(keyName) ?? '');
            map.set(keyName, value);
        } catch (e) {
            console.error(e);
        }
    }

    return map;
}

const localStorageData: Map<LocalStorageKeys, LocalStorageData[LocalStorageKeys]> = getLocalStorageData();

export const useLocalStorage = () => {
    const get = <T extends LocalStorageKeys>(keyName: T) => {
        return (localStorageData.get(keyName) ?? null) as LocalStorageData[T] | null;
    };

    const set = <T extends LocalStorageKeys>(keyName: T, value: LocalStorageData[T]) => {
        try {
            localStorageData.set(keyName, value);
            localStorage.setItem(keyName, JSON.stringify(value));
        } catch (e) {
            console.error(e);
        }
    };

    return { get, set };
};
