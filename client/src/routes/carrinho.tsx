import { createFileRoute } from '@tanstack/react-router';

import Carrinho from '@/lib/pages/shoppingCart';

export const Route = createFileRoute('/carrinho')({
  component: Carrinho,
});
