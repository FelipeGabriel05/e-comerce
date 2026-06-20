import { ShoppingCart } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { useCart } from '@/lib/hooks/use-cart';
import type { Product } from '@/lib/types/product';

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
    <div className="flex flex-col rounded-xl border border-white/10 bg-white/5 hover:border-white/25 hover:bg-white/10 transition-all overflow-hidden w-64">
      <div className="w-full h-40 bg-white/10 flex items-center justify-center overflow-hidden">
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

      <div className="flex flex-col gap-3 p-4">
        <p className="text-white font-medium">{product.descricao}</p>
        <p className="text-violet-400 font-bold text-lg">{formattedPrice}</p>
        <Button
          onClick={() => addToCart(product.id)}
          className="w-full bg-violet-600 hover:bg-violet-500 text-white"
        >
          <ShoppingCart size={14} />
          Adicionar ao Carrinho
        </Button>
      </div>
    </div>
  );
};
