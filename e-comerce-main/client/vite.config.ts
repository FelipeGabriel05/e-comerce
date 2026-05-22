import { ValidateEnv } from '@julr/vite-plugin-validate-env';
import babel from '@rolldown/plugin-babel';
import tailwindcss from '@tailwindcss/vite';
import { devtools } from '@tanstack/devtools-vite';
import { tanstackRouter } from '@tanstack/router-plugin/vite';
import react, { reactCompilerPreset } from '@vitejs/plugin-react';
import checker from 'vite-plugin-checker';
import { defineConfig } from 'vite-plus';

// https://vitejs.dev/config/
export default defineConfig({
  staged: {
    '*.{js,jsx,ts,tsx,json,jsonc,css,scss,md,mdx}': [
      "bash -c 'npm run type:check'",
      "bash -c 'npm run biome:check'",
    ],
  },

  plugins: [
    ValidateEnv(),
    devtools(),
    tanstackRouter({ autoCodeSplitting: true }),
    react(),
    ...(process.env.VITE_PLUGIN_REACT_COMPILER === 'true'
      ? [
          babel({
            presets: [reactCompilerPreset()],
          }),
        ]
      : []),
    tailwindcss(),
    ...(process.env.NODE_ENV === 'production' || process.env.VITEST
      ? []
      : [
          checker({
            typescript: true,
          }),
        ]),
  ],

  server: {
    open: true,
  },

  resolve: {
    tsconfigPaths: true,
  },

  test: {
    coverage: {
      provider: 'v8',
      include: ['src/utils/**/**.{ts,tsx,js,jsx}'],
    },
  },
});
