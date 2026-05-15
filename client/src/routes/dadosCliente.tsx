import { createFileRoute } from '@tanstack/react-router';

import DadosPessoaisPage from '@/lib/pages/clients/dados-pessoais';

export const Route = createFileRoute('/dadosCliente')({
  component: DadosPessoaisPage,
});
