import { createFileRoute } from '@tanstack/react-router';

import Vendas from '@/lib/pages/admin/pages-admin/vendas';

export const Route = createFileRoute('/admin/vendas')({
  component: Vendas,
});
