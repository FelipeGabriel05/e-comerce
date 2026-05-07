import type { Product } from '@/lib/types/product';
import { useCart } from '@/lib/context/cart-context';

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
      {/* Imagem */}
      <div className="aspect-video w-full rounded-lg bg-white/10 flex items-center justify-center">
        <span className="text-white/20 text-sm">Sem imagem</span>
      </div>

      {/* Descrição */}
      <p className="text-white font-medium">{product.descricao}</p>

      {/* Preço */}
      <p className="text-violet-400 font-bold text-lg">{formattedPrice}</p>

      {/* Botão */}
      <button
        type="button"
        onClick={() => addToCart(product)}
        className="w-full rounded-lg bg-violet-600 py-2 text-sm font-semibold text-white hover:bg-violet-500 transition-colors"
      >
        Adicionar ao Carrinho
      </button>
    </div>
  );
};