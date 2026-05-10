import { createFileRoute } from '@tanstack/react-router';

import MainClient from '@/lib/pages/clients';

export const Route = createFileRoute('/cliente')({
  component: MainClient,
});
