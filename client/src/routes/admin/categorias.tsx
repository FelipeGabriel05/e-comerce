import { createFileRoute } from '@tanstack/react-router';

import Categorias from '@/lib/pages/admin/pages-admin/categorias';

export const Route = createFileRoute('/admin/categorias')({
  component: Categorias,
});
