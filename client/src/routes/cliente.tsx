import { createFileRoute } from '@tanstack/react-router';

import MainClientPage from '@/lib/pages/clients';

export const Route = createFileRoute('/cliente')({
  component: MainClientPage,
});
