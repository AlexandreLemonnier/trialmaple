type Env = Record<string, string>;

type LoadEnv = (mode: string, envDir: string) => Env;

type ProxyOptions = {
    target: string;
    rewrite: (path: string) => string;
    changeOrigin: boolean;
};

type ServerOptions = {
    open?: boolean;
    strictPort?: boolean;
    port?: number;
    proxy?: Record<string, ProxyOptions>;
};

type VueViteConfig = {
    plugins: unknown[];
    resolve: {
        alias: Record<string, string>;
    };
    server: ServerOptions;
};

type CreateVueViteConfigOptions = {
    appRootUrl: URL;
    mode: string;
    loadEnv: LoadEnv;
    plugins: unknown[];
    server?: {
        open?: boolean;
        strictPort?: boolean;
        portEnvName?: string;
        proxy?: boolean;
        proxiedApiUrlPrefixEnvName?: string;
        apiUrlEnvName?: string;
    };
};

export function createVueViteConfig(options: CreateVueViteConfigOptions): VueViteConfig {
    const env = options.loadEnv(options.mode, process.cwd());
    process.env = { ...process.env, ...env };

    const serverConfig = {
        open: true,
        strictPort: true,
        portEnvName: 'VITE_APP_PORT',
        proxy: true,
        proxiedApiUrlPrefixEnvName: 'VITE_PROXIED_API_URL_PREFIX',
        apiUrlEnvName: 'VITE_API_URL',
        ...options.server
    };

    const server: ServerOptions = {
        open: serverConfig.open,
        strictPort: serverConfig.strictPort
    };

    const port = env[serverConfig.portEnvName];

    if (port) {
        server.port = Number.parseInt(port);
    }

    const proxiedApiUrlPrefix = env[serverConfig.proxiedApiUrlPrefixEnvName];
    const apiUrl = env[serverConfig.apiUrlEnvName];

    if (serverConfig.proxy && proxiedApiUrlPrefix && apiUrl) {
        server.proxy = {
            [proxiedApiUrlPrefix]: {
                target: apiUrl,
                rewrite: (path) => path.replace(proxiedApiUrlPrefix, ''),
                changeOrigin: true
            }
        };
    }

    return {
        plugins: options.plugins,
        resolve: {
            alias: {
                '#': new URL('src', options.appRootUrl).pathname
            }
        },
        server
    };
}
