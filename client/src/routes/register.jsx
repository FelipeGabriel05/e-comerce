import { createFileRoute } from '@tanstack/react-router';

import Register from '@/lib/pages/register';

export const Route = createFileRoute('/register')({
  component: Register,
});
