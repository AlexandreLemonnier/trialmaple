// src/composables/useAuth.ts
import { jwtDecode } from 'jwt-decode';
import { ref } from 'vue';

type JwtPayload = {
    expirationTimeMs: number;
    username: string;
};

export function useAuth() {
    const isAuthenticated = ref(false);

    const checkTokenValidity = () => {
        const token = localStorage.getItem('auth_token');
        if (!token) {
            isAuthenticated.value = false;
            return;
        }

        try {
            const decoded = jwtDecode<JwtPayload>(token);
            const currentTime = Date.now() / 1000;

            if (decoded.expirationTimeMs < currentTime) {
                // Expired token
                localStorage.removeItem('auth_token');
                isAuthenticated.value = false;
            } else {
                isAuthenticated.value = true;
            }
        } catch (e) {
            // Corrupted or invalid token
            console.error('Invalid token:', e);
            localStorage.removeItem('auth_token');
            isAuthenticated.value = false;
        }
    };

    return { isAuthenticated, checkTokenValidity };
}
