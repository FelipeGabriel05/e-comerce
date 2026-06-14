import { createFileRoute } from '@tanstack/react-router';

import AlterarAdmin from '@/lib/pages/admin/pages-admin/alterar';

export const Route = createFileRoute('/admin/alterar')({
  component: AlterarAdmin,
});
