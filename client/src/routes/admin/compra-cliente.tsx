import { createFileRoute } from '@tanstack/react-router';

import ComprasCLiente from '@/lib/pages/admin/pages-admin/reports/compras-cliente';

export const Route = createFileRoute('/admin/compra-cliente')({
  component: ComprasCLiente,
});
