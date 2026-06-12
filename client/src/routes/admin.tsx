import { createFileRoute } from '@tanstack/react-router';

import Admin from '@/lib/pages/admin/index';

export const Route = createFileRoute('/admin')({
  component: Admin,
});
