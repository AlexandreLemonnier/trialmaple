export const useEnv = () => {
    return {
        PROXIED_API_URL_PREFIX: import.meta.env.VITE_PROXIED_API_URL_PREFIX as string,
        PROXIED_BACKUP_API_URL_PREFIX: import.meta.env.VITE_PROXIED_BACKUP_API_URL_PREFIX as string,
        API_TOKEN: import.meta.env.VITE_API_TOKEN as string
    };
};
