import { createFileRoute } from '@tanstack/react-router';

import CarrinhoPage from '@/lib/pages/shoppingCart';

export const Route = createFileRoute('/carrinho')({
  component: CarrinhoPage,
});
