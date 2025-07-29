import stylistic from '@stylistic/eslint-plugin';
import typescriptESLintPlugin from '@typescript-eslint/eslint-plugin';
import tsParser from '@typescript-eslint/parser';
import eslintPluginVue from 'eslint-plugin-vue';
import globals from 'globals';
import typescriptESLint from 'typescript-eslint';
import vueESLintParser from 'vue-eslint-parser';

export default typescriptESLint.config(
    {
        ignores: [
            'dist/**',
            '.output/**',
            'node_modules/**',
            'public/**',
            '**/*.d.ts'
        ],
        files: ['**/*.{ts,js,vue}'],
        languageOptions: {
            ecmaVersion: 'latest',
            sourceType: 'module',
            globals: {
                ...globals.node,
                ...globals.browser
            },
            parser: vueESLintParser,
            parserOptions: {
                tsconfigRootDir: './',
                project: 'tsconfig.json',
                parser: tsParser,
                extraFileExtensions: ['.vue']
            }
        },
        plugins: {
            '@stylistic': stylistic,
            'vue': eslintPluginVue,
            '@typescript-eslint': typescriptESLintPlugin
        },
        rules: {
            '@stylistic/array-bracket-newline': ['error', 'consistent'],
            '@stylistic/array-bracket-spacing': ['error', 'never'],
            '@stylistic/array-element-newline': ['error', 'consistent'],
            '@stylistic/arrow-parens': ['error', 'always'],
            '@stylistic/arrow-spacing': ['error', {
                after: true,
                before: true
            }],
            '@stylistic/block-spacing': ['error', 'always'],
            '@stylistic/brace-style': ['error', '1tbs'],
            '@stylistic/comma-dangle': ['error', 'never'],
            '@stylistic/comma-spacing': ['error', {
                after: true,
                before: false
            }],
            '@stylistic/computed-property-spacing': ['error', 'never'],
            '@stylistic/eol-last': ['error', 'always'],
            '@stylistic/function-call-spacing': ['error', 'never'],
            '@stylistic/function-call-argument-newline': ['error', 'consistent'],
            '@stylistic/indent': ['error', 4],
            '@stylistic/key-spacing': ['error', {
                afterColon: true,
                beforeColon: false,
                mode: 'strict'
            }],
            '@stylistic/keyword-spacing': ['error', {
                after: true,
                before: true
            }],
            '@stylistic/max-statements-per-line': ['error', {
                max: 1
            }],
            '@stylistic/new-parens': ['error', 'always'],
            '@stylistic/no-extra-parens': ['error', 'all'],
            '@stylistic/no-extra-semi': ['error'],
            '@stylistic/no-floating-decimal': ['error'],
            '@stylistic/no-multi-spaces': ['error'],
            '@stylistic/no-multiple-empty-lines': ['error', {
                max: 2
            }],
            '@stylistic/no-tabs': ['error'],
            '@stylistic/no-trailing-spaces': ['error'],
            '@stylistic/no-whitespace-before-property': ['error'],
            '@stylistic/object-curly-spacing': ['error', 'always'],
            '@stylistic/padded-blocks': ['error', 'never'],
            '@stylistic/quotes': ['error', 'single', {
                allowTemplateLiterals: 'always'
            }],
            '@stylistic/rest-spread-spacing': ['error', 'never'],
            '@stylistic/semi': ['error', 'always'],
            '@stylistic/semi-spacing': ['error', {
                after: true,
                before: false
            }],
            '@stylistic/space-before-blocks': ['error', 'always'],
            '@stylistic/space-before-function-paren': ['error', {
                anonymous: 'never',
                asyncArrow: 'always',
                named: 'never'
            }],
            '@stylistic/space-in-parens': ['error', 'never'],
            '@stylistic/space-infix-ops': ['error'],
            '@stylistic/space-unary-ops': ['error', {
                nonwords: false,
                words: true
            }],
            '@stylistic/spaced-comment': ['error', 'always'],
            '@stylistic/switch-colon-spacing': ['error', {
                after: true,
                before: false
            }],
            '@stylistic/template-curly-spacing': ['error', 'never'],
            '@stylistic/wrap-regex': ['error'],
            'vue/component-api-style': ['error', ['script-setup', 'composition']],
            'vue/no-child-content': ['error'],
            'vue/no-deprecated-data-object-declaration': ['error'],
            'vue/no-deprecated-destroyed-lifecycle': ['error'],
            'vue/no-deprecated-dollar-listeners-api': ['error'],
            'vue/no-deprecated-dollar-scopedslots-api': ['error'],
            'vue/no-deprecated-events-api': ['error'],
            'vue/no-deprecated-filter': ['error'],
            'vue/no-deprecated-functional-template': ['error'],
            'vue/no-deprecated-html-element-is': ['error'],
            'vue/no-deprecated-inline-template': ['error'],
            'vue/no-deprecated-props-default-this': ['error'],
            'vue/no-deprecated-router-link-tag-prop': ['error'],
            'vue/no-deprecated-scope-attribute': ['error'],
            'vue/no-deprecated-slot-attribute': ['error'],
            'vue/no-deprecated-slot-scope-attribute': ['error'],
            'vue/no-deprecated-v-bind-sync': ['error'],
            'vue/no-deprecated-v-is': ['error'],
            'vue/no-deprecated-v-on-native-modifier': ['error'],
            'vue/no-deprecated-v-on-number-modifiers': ['error'],
            'vue/no-deprecated-vue-config-keycodes': ['error'],
            'vue/no-dupe-keys': ['error'],
            'vue/no-dupe-v-else-if': ['error'],
            'vue/no-duplicate-attributes': ['error', {
                allowCoexistClass: true,
                allowCoexistStyle: true
            }],
            'vue/no-export-in-script-setup': ['error'],
            'vue/no-lifecycle-after-await': ['error'],
            'vue/no-mutating-props': ['error'],
            'vue/no-parsing-error': ['error'],
            'vue/no-ref-as-operand': ['error'],
            'vue/no-reserved-component-names': ['error'],
            'vue/no-reserved-keys': ['error'],
            'vue/no-reserved-props': ['error'],
            'vue/no-side-effects-in-computed-properties': ['error'],
            'vue/no-template-key': ['error'],
            'vue/no-textarea-mustache': ['error'],
            'vue/no-unused-components': ['error'],
            'vue/no-unused-vars': ['error'],
            'vue/no-use-v-if-with-v-for': ['error'],
            'vue/no-useless-template-attributes': ['error'],
            'vue/no-v-for-template-key-on-child': ['error'],
            'vue/no-watch-after-await': ['error'],
            'vue/prefer-import-from-vue': ['error'],
            'vue/require-component-is': ['error'],
            'vue/require-prop-type-constructor': ['error'],
            'vue/require-toggle-inside-transition': ['error'],
            'vue/require-valid-default-prop': ['error'],
            'vue/return-in-computed-property': ['error'],
            'vue/return-in-emits-validator': ['error'],
            'vue/valid-attribute-name': ['error'],
            'vue/valid-define-emits': ['error'],
            'vue/valid-define-props': ['error'],
            'vue/valid-template-root': ['error'],
            'vue/valid-v-bind': ['error'],
            'vue/valid-v-on': ['error'],
            'vue/valid-v-model': ['error'],
            'vue/valid-v-if': ['error'],
            'vue/valid-v-html': ['error'],
            'vue/valid-v-for': ['error'],
            'vue/valid-v-else': ['error'],
            'vue/valid-v-else-if': ['error'],
            'vue/valid-v-pre': ['error'],
            'vue/valid-v-show': ['error'],
            'vue/valid-v-slot': ['error'],
            'vue/attribute-hyphenation': ['error'],
            'vue/component-definition-name-casing': ['error', 'PascalCase'],
            'vue/first-attribute-linebreak': ['error', {
                singleline: 'beside',
                multiline: 'beside'
            }],
            'vue/html-closing-bracket-newline': ['error', {
                singleline: 'never',
                multiline: 'never'
            }],
            'vue/html-closing-bracket-spacing': ['error', {
                startTag: 'never',
                endTag: 'never',
                selfClosingTag: 'always'
            }],
            'vue/html-end-tags': ['error'],
            'vue/html-indent': ['error', 4],
            'vue/html-quotes': ['error', 'double', {
                avoidEscape: false
            }],
            'vue/mustache-interpolation-spacing': ['error', 'always'],
            'vue/no-multi-spaces': ['error'],
            'vue/no-spaces-around-equal-signs-in-attribute': ['error'],
            'vue/no-template-shadow': ['error'],
            'vue/one-component-per-file': ['error'],
            'vue/prop-name-casing': ['error', 'camelCase'],
            'vue/require-explicit-emits': ['error'],
            'vue/require-prop-types': ['error'],
            'vue/v-bind-style': ['error', 'shorthand'],
            'vue/v-on-event-hyphenation': ['error', 'never'],
            'vue/v-on-style': ['error', 'shorthand'],
            'vue/v-slot-style': ['error', {
                atComponent: 'shorthand',
                default: 'shorthand',
                named: 'shorthand'
            }],
            'vue/no-lone-template': ['error'],
            'vue/this-in-template': ['error', 'never'],
            'vue/block-lang': ['error', {
                script: {
                    lang: 'ts'
                }
            }],
            'vue/block-order': ['error', {
                order: ['template', 'script', 'style']
            }],
            'vue/component-name-in-template-casing': ['error', 'PascalCase'],
            'vue/custom-event-name-casing': ['error', 'camelCase'],
            'vue/define-emits-declaration': ['error', 'type-based'],
            'vue/define-macros-order': ['error', {
                order: ['defineProps', 'defineEmits']
            }],
            'vue/define-props-declaration': ['error', 'type-based'],
            'vue/html-button-has-type': ['error'],
            'vue/html-comment-content-newline': ['error'],
            'vue/html-comment-content-spacing': ['error'],
            'vue/html-comment-indent': ['error', 4],
            'vue/match-component-import-name': ['error'],
            'vue/no-empty-component-block': ['error'],
            'vue/no-multiple-objects-in-class': ['error'],
            'vue/no-unused-properties': ['error'],
            'vue/no-unused-refs': ['error'],
            'vue/no-useless-mustaches': ['error'],
            'vue/no-useless-v-bind': ['error'],
            'vue/no-v-text': ['error'],
            'vue/padding-line-between-blocks': ['error', 'always'],
            'vue/prefer-separate-static-class': ['error'],
            'vue/require-direct-export': ['error'],
            'vue/require-macro-variable-name': ['error', {
                defineProps: 'props',
                defineEmits: 'emit',
                defineSlots: 'slots',
                useSlots: 'slots',
                useAttrs: 'attrs'
            }],
            'vue/require-typed-ref': ['error'],
            'vue/v-for-delimiter-style': ['error', 'in'],
            'vue/array-bracket-newline': ['error', 'consistent'],
            'vue/array-bracket-spacing': ['error', 'never'],
            'vue/array-element-newline': ['error', 'consistent'],
            'vue/arrow-spacing': ['error', {
                after: true,
                before: true
            }],
            'vue/block-spacing': ['error', 'always'],
            'vue/brace-style': ['error', '1tbs'],
            'vue/comma-dangle': ['error', 'never'],
            'vue/comma-spacing': ['error', {
                after: true,
                before: false
            }],
            'vue/func-call-spacing': ['error', 'never'],
            'vue/key-spacing': ['error', {
                afterColon: true,
                beforeColon: false,
                mode: 'strict'
            }],
            'vue/keyword-spacing': ['error', {
                after: true,
                before: true
            }],
            'vue/no-constant-condition': ['error'],
            'vue/no-extra-parens': ['error', 'all'],
            'vue/no-irregular-whitespace': ['error', {
                skipComments: true,
                skipRegExps: true,
                skipStrings: true,
                skipTemplates: true
            }],
            'vue/no-sparse-arrays': ['error'],
            'vue/no-useless-concat': ['error'],
            'vue/object-curly-spacing': ['error', 'always'],
            'vue/prefer-template': ['error'],
            'vue/quote-props': ['error', 'consistent-as-needed'],
            'vue/space-in-parens': ['error', 'never'],
            'vue/space-infix-ops': ['error'],
            'vue/space-unary-ops': ['error', {
                nonwords: false,
                words: true
            }],
            'vue/template-curly-spacing': ['error', 'never'],
            '@typescript-eslint/adjacent-overload-signatures': 'error',
            '@typescript-eslint/array-type': ['error', {
                default: 'array'
            }],
            '@typescript-eslint/await-thenable': 'error',
            '@typescript-eslint/class-literal-property-style': ['error', 'fields'],
            '@typescript-eslint/consistent-generic-constructors': ['error', 'type-annotation'],
            '@typescript-eslint/consistent-indexed-object-style': ['error', 'record'],
            '@typescript-eslint/consistent-type-definitions': ['error', 'type'],
            '@typescript-eslint/consistent-type-exports': ['error', {
                fixMixedExportsWithInlineTypeSpecifier: false
            }],
            '@typescript-eslint/consistent-type-imports': ['error', {
                fixStyle: 'separate-type-imports',
                prefer: 'type-imports'
            }],
            '@typescript-eslint/default-param-last': 'error',
            '@typescript-eslint/method-signature-style': ['error', 'method'],
            '@typescript-eslint/no-confusing-non-null-assertion': 'error',
            '@typescript-eslint/no-duplicate-type-constituents': 'error',
            '@typescript-eslint/no-empty-interface': 'error',
            '@typescript-eslint/no-explicit-any': 'error',
            '@typescript-eslint/no-extra-non-null-assertion': 'error',
            '@typescript-eslint/no-for-in-array': 'error',
            '@typescript-eslint/no-import-type-side-effects': 'error',
            '@typescript-eslint/no-invalid-void-type': 'error',
            '@typescript-eslint/no-meaningless-void-operator': 'error',
            '@typescript-eslint/no-misused-new': 'error',
            '@typescript-eslint/no-mixed-enums': 'error',
            '@typescript-eslint/no-namespace': 'error',
            '@typescript-eslint/no-require-imports': 'error',
            '@typescript-eslint/no-shadow': 'error',
            '@typescript-eslint/no-this-alias': 'error',
            '@typescript-eslint/no-unnecessary-boolean-literal-compare': 'error',
            '@typescript-eslint/no-unnecessary-qualifier': 'error',
            '@typescript-eslint/no-unnecessary-type-constraint': 'error',
            '@typescript-eslint/no-unsafe-declaration-merging': 'error',
            '@typescript-eslint/no-useless-empty-export': 'error',
            '@typescript-eslint/prefer-enum-initializers': 'error',
            '@typescript-eslint/prefer-for-of': 'error',
            '@typescript-eslint/prefer-function-type': 'error',
            '@typescript-eslint/prefer-includes': 'error',
            '@typescript-eslint/prefer-optional-chain': 'error',
            '@typescript-eslint/prefer-readonly': 'error',
            '@typescript-eslint/prefer-reduce-type-parameter': 'error',
            '@typescript-eslint/prefer-string-starts-ends-with': 'error',
            '@typescript-eslint/promise-function-async': 'error',
            '@typescript-eslint/require-array-sort-compare': 'error',
            '@typescript-eslint/restrict-plus-operands': 'error',
            '@typescript-eslint/restrict-template-expressions': 'error',
            'array-callback-return': ['error', {
                allowImplicit: true,
                checkForEach: true
            }],
            'constructor-super': ['error'],
            'for-direction': ['error'],
            'line-comment-position': ['error', {
                position: 'above'
            }],
            'no-array-constructor': ['error'],
            'no-async-promise-executor': ['error'],
            'no-class-assign': ['error'],
            'no-compare-neg-zero': ['error'],
            'no-cond-assign': ['error'],
            'no-const-assign': ['error'],
            'no-constant-condition': ['error'],
            'no-constructor-return': ['error'],
            'no-debugger': ['error'],
            'no-delete-var': ['error'],
            'no-dupe-args': ['error'],
            'no-dupe-class-members': ['error'],
            'no-dupe-else-if': ['error'],
            'no-dupe-keys': ['error'],
            'no-duplicate-case': ['error'],
            'no-else-return': ['error', {
                allowElseIf: true
            }],
            'no-empty': ['error', {
                allowEmptyCatch: false
            }],
            'no-empty-pattern': ['error'],
            'no-empty-static-block': ['error'],
            'no-eq-null': ['error'],
            'no-eval': ['error'],
            'no-ex-assign': ['error'],
            'no-extra-boolean-cast': ['error'],
            'no-fallthrough': ['error'],
            'no-func-assign': ['error'],
            'no-global-assign': ['error'],
            'no-implied-eval': ['error'],
            'no-import-assign': ['error'],
            'no-inline-comments': ['error'],
            'no-inner-declarations': ['error'],
            'no-invalid-this': ['error'],
            'no-irregular-whitespace': ['error', {
                skipComments: true,
                skipRegExps: true,
                skipStrings: true,
                skipTemplates: true
            }],
            'no-lone-blocks': ['error'],
            'no-loop-func': ['error'],
            'no-multi-assign': ['error'],
            'no-multi-str': ['error'],
            'no-nested-ternary': ['error'],
            'no-new': ['error'],
            'no-new-func': ['error'],
            'no-new-object': ['error'],
            'no-nonoctal-decimal-escape': ['error'],
            'no-octal': ['error'],
            'no-octal-escape': ['error'],
            'no-param-reassign': ['error'],
            'no-return-assign': ['error'],
            'no-script-url': ['error'],
            'no-self-assign': ['error'],
            'no-self-compare': ['error'],
            'no-setter-return': ['error'],
            'no-shadow-restricted-names': ['error'],
            'no-sparse-arrays': ['error'],
            'no-this-before-super': ['error'],
            'no-throw-literal': ['error'],
            'no-undef-init': ['error'],
            'no-underscore-dangle': ['error'],
            'no-unexpected-multiline': ['error'],
            'no-unmodified-loop-condition': ['error'],
            'no-unneeded-ternary': ['error'],
            'no-unreachable': ['error'],
            'no-unreachable-loop': ['error'],
            'no-unsafe-finally': ['error'],
            'no-unsafe-negation': ['error'],
            'no-unsafe-optional-chaining': ['error'],
            'no-use-before-define': ['error'],
            'no-useless-catch': ['error'],
            'no-useless-computed-key': ['error'],
            'no-useless-concat': ['error'],
            'no-useless-escape': ['error'],
            'no-useless-rename': ['error'],
            'no-useless-return': ['error'],
            'no-var': ['error'],
            'no-void': ['error'],
            'no-with': ['error'],
            'object-shorthand': ['error'],
            'prefer-arrow-callback': ['error', {
                allowNamedFunctions: true,
                allowUnboundThis: false
            }],
            'prefer-const': ['error', {
                destructuring: 'any',
                ignoreReadBeforeAssign: false
            }],
            'prefer-exponentiation-operator': ['error'],
            'prefer-object-has-own': ['error'],
            'prefer-object-spread': ['error'],
            'prefer-promise-reject-errors': ['error', {
                allowEmptyReject: false
            }],
            'prefer-regex-literals': ['error'],
            'prefer-rest-params': ['error'],
            'prefer-spread': ['error'],
            'prefer-template': ['error'],
            radix: ['error', 'as-needed'],
            'semi-style': ['error', 'last'],
            'use-isnan': ['error'],
            'valid-typeof': ['error'],
            yoda: ['error', 'never']
        }
    }
);