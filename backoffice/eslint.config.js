import stylistic from '@stylistic/eslint-plugin';
import typescriptESLintPlugin from '@typescript-eslint/eslint-plugin';
import tsParser from '@typescript-eslint/parser';
import eslintPluginVue from 'eslint-plugin-vue';
import globals from 'globals';
import { defineConfig } from 'eslint/config';
import vueESLintParser from 'vue-eslint-parser';

import { createVueEslintConfig } from '../configs/eslint/vue.js';

export default createVueEslintConfig({
    defineConfig,
    stylistic,
    typescriptESLintPlugin,
    tsParser,
    eslintPluginVue,
    globals,
    vueESLintParser,
    tsconfigRootDir: new URL('.', import.meta.url).pathname,
    project: [
        './tsconfig.app.json',
        './tsconfig.node.json'
    ]
});
