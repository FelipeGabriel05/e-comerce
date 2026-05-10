import { createFileRoute } from '@tanstack/react-router';

import dadosPessoais from '@/lib/pages/clients/dados-pessoais';

export const Route = createFileRoute('/dadosCliente')({
  component: dadosPessoais,
});
