import type { Guess } from '#/types/api/guess';

const localStorageTypeMap = {
    history: {} as Record<string, Guess>,
    currentDailyMapUuid: ''
};
type LocalStorageData = typeof localStorageTypeMap;
type LocalStorageKeys = keyof LocalStorageData;

function getLocalStorageData() {
    const map = new Map();
    for (const keyName of Object.keys(localStorageTypeMap)) {
        try {
            const rawValue = localStorage.getItem(keyName);
            let value;
            try {
                value = JSON.parse(rawValue as string);
            } catch (e) {
                value = rawValue;
            }
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

    const remove = <T extends LocalStorageKeys>(keyName: T) => {
        localStorage.removeItem(keyName);
    };

    return { get, set, remove };
};
