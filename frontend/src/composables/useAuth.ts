// src/composables/useAuth.ts
import { jwtDecode } from 'jwt-decode';
import { ref } from 'vue';
import {
    AUTH_TOKEN_INVALID_EVENT,
    DEFAULT_AUTH_TOKEN_STORAGE_KEY,
    notifyAuthTokenInvalid
} from '@tm-trialmaple/shared/composables/authToken';

type JwtPayload = {
    exp?: number;
    username?: string;
};

const isAuthenticated = ref(false);
let expirationTimeout: ReturnType<typeof globalThis.setTimeout> | undefined;
const MAX_TIMEOUT_MS = 2_147_483_647;

export function useAuth() {
    const clearExpirationTimeout = () => {
        if (expirationTimeout !== undefined) {
            globalThis.clearTimeout(expirationTimeout);
            expirationTimeout = undefined;
        }
    };

    const clearAuthToken = (notify = false) => {
        clearExpirationTimeout();
        localStorage.removeItem(DEFAULT_AUTH_TOKEN_STORAGE_KEY);
        isAuthenticated.value = false;

        if (notify) {
            notifyAuthTokenInvalid(DEFAULT_AUTH_TOKEN_STORAGE_KEY);
        }
    };

    const scheduleExpiration = (expirationTimeMs: number) => {
        clearExpirationTimeout();

        const remainingMs = expirationTimeMs - Date.now();
        if (remainingMs <= 0) {
            clearAuthToken(true);
            return;
        }

        expirationTimeout = globalThis.setTimeout(() => {
            if (remainingMs > MAX_TIMEOUT_MS) {
                scheduleExpiration(expirationTimeMs);
                return;
            }

            clearAuthToken(true);
        }, Math.min(remainingMs, MAX_TIMEOUT_MS));
    };

    const checkTokenValidity = () => {
        const token = localStorage.getItem(DEFAULT_AUTH_TOKEN_STORAGE_KEY);
        if (!token) {
            clearExpirationTimeout();
            isAuthenticated.value = false;
            return;
        }

        try {
            const decoded = jwtDecode<JwtPayload>(token);
            const expirationTimeMs = decoded.exp ? decoded.exp * 1000 : 0;

            if (expirationTimeMs <= Date.now()) {
                clearAuthToken();
            } else {
                isAuthenticated.value = true;
                scheduleExpiration(expirationTimeMs);
            }
        } catch (e) {
            // Corrupted or invalid token
            console.error('Invalid token:', e);
            clearAuthToken();
        }
    };

    const storeAuthToken = (token: string) => {
        localStorage.setItem(DEFAULT_AUTH_TOKEN_STORAGE_KEY, token);
        checkTokenValidity();
    };

    return {
        AUTH_TOKEN_INVALID_EVENT,
        isAuthenticated,
        checkTokenValidity,
        clearAuthToken,
        storeAuthToken
    };
}
