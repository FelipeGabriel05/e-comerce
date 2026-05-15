import { createFileRoute } from '@tanstack/react-router';

import HistoricoPedidosPage from '@/lib/pages/clients/historico-pedidos';

export const Route = createFileRoute('/historicoCliente')({
  component: HistoricoPedidosPage,
});
