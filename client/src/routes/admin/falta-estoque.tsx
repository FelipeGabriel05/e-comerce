import { createFileRoute } from '@tanstack/react-router';

import FaltaEstoque from '@/lib/pages/admin/pages-admin/reports/falta-estoque';

export const Route = createFileRoute('/admin/falta-estoque')({
  component: FaltaEstoque,
});
