import { createFileRoute } from '@tanstack/react-router';

import historicoPedidos from '@/lib/pages/clients/historicoPedidos';

export const Route = createFileRoute('/historicoCliente')({
  component: historicoPedidos,
});
