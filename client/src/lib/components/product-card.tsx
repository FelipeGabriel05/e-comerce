import { ShoppingCart } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { P } from '@/components/ui/typography.tsx';
import { useCart } from '@/lib/hooks/use-cart';
import type { Product } from '@/lib/types/product.ts';

type ProductCardProps = {
  product: Product;
};

export const ProductCard = ({ product }: ProductCardProps) => {
  const { addToCart } = useCart();

  const formattedPrice = new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(product.preco);

  return (
    <div className="flex flex-col gap-3 rounded-xl border border-white/10 bg-white/5 p-4 hover:border-white/25 hover:bg-white/10 transition-all">
      <div className="aspect-video w-full min-h-40 rounded-lg bg-white/10 flex items-center justify-center overflow-hidden">
        {product.foto ? (
          <img
            src={product.foto}
            alt={product.descricao}
            className="w-full h-full object-cover"
          />
        ) : (
          <span className="text-white/20 text-sm">Sem imagem</span>
        )}
      </div>

      <P className="text-white font-medium">{product.descricao}</P>

      <P className="text-violet-400 font-bold text-lg">{formattedPrice}</P>

      <Button
        onClick={() => addToCart(product.id)}
        className="w-full bg-violet-600 hover:bg-violet-500 text-white"
      >
        <ShoppingCart size={14} />
        Adicionar ao Carrinho
      </Button>
    </div>
  );
};
