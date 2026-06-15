import { createFileRoute } from '@tanstack/react-router';

import ValorRecebido from '@/lib/pages/admin/pages-admin/valor-recebido';

export const Route = createFileRoute('/admin/valor-recebido')({
  component: ValorRecebido,
});
