import { createFileRoute } from '@tanstack/react-router';

import Admin from '@/lib/pages/admin';

export const Route = createFileRoute('/admin/')({
  component: Admin,
});
