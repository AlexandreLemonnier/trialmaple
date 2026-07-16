export const useEnv = () => {
    return {
        PROXIED_API_URL_PREFIX: import.meta.env.VITE_PROXIED_API_URL_PREFIX as string,
        DISCORD_CLIENT_ID: '1511852531100160010'
    };
};
