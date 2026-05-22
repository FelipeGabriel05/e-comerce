import type { KnipConfig } from 'knip';

const config: KnipConfig = {
	entry: ['src/main.tsx'],
	ignore: ['src/**/*.gen.ts', 'src/components/ui/**'],
	project: ['src/**/*.{ts,tsx,js,jsx,css,scss}'],
	ignoreBinaries: ['changelogithub'],
	ignoreDependencies: ["lucide-react"]
};

export default config;
