import { createFileRoute } from '@tanstack/react-router';

import AdminProducts from '@/lib/pages/admin/products';

export const Route = createFileRoute('/admin/products')({
  component: AdminProducts,
});
