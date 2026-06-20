import { createFileRoute } from '@tanstack/react-router';

import Produtos from '@/lib/pages/admin/pages-admin/produtos';

export const Route = createFileRoute('/admin/produtos')({
  component: Produtos,
});
