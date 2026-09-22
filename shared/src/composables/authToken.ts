export const DEFAULT_AUTH_TOKEN_STORAGE_KEY = 'auth_token';
export const AUTH_TOKEN_INVALID_EVENT = 'auth-token-invalid';

export type AuthTokenInvalidDetail = {
    authTokenStorageKey: string;
};

type JwtPayload = {
    exp?: number;
};

function isExpiredJwt(token: string) {
    try {
        const payload = token.split('.')[1];
        if (!payload) return true;

        const decoded = JSON.parse(globalThis.atob(payload.replace(/-/g, '+').replace(/_/g, '/'))) as JwtPayload;
        return !decoded.exp || decoded.exp * 1000 <= Date.now();
    } catch {
        return true;
    }
}

export function getUsableAuthToken(authTokenStorageKey: string) {
    const token = localStorage.getItem(authTokenStorageKey);
    if (!token || !isExpiredJwt(token)) return token;

    localStorage.removeItem(authTokenStorageKey);
    notifyAuthTokenInvalid(authTokenStorageKey);
    return null;
}

export function notifyAuthTokenInvalid(authTokenStorageKey: string) {
    globalThis.dispatchEvent(new CustomEvent<AuthTokenInvalidDetail>(AUTH_TOKEN_INVALID_EVENT, {
        detail: { authTokenStorageKey }
    }));
}
