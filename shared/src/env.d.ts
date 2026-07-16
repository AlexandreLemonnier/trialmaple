interface ImportMetaEnv {
    readonly VITE_PROXIED_API_URL_PREFIX: string;
}

interface ImportMeta {
    readonly env: ImportMetaEnv;
}
