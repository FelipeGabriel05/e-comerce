import { createFileRoute } from '@tanstack/react-router';

import historicoPedidos from '@/lib/pages/clients/historico-pedidos';

export const Route = createFileRoute('/historicoCliente')({
  component: historicoPedidos,
});
