import { ShoppingCart } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { useCart } from '@/lib/hooks/use-cart';

export const Header = () => {
  const { totalItems } = useCart();

  return (
    <header className="sticky top-0 z-10 w-full bg-base-100/80 backdrop-blur-md">
      <section className="wrapper mx-auto flex items-center justify-between py-2 px-4">
        <p className="text-white font-bold">Games Store</p>

        <Button
          variant="outline"
          className="relative flex items-center gap-2 text-white/70 hover:text-white"
        >
          <ShoppingCart size={16} />
          Carrinho
          {totalItems > 0 && (
            <span className="absolute -right-2 -top-2 flex h-5 w-5 items-center justify-center rounded-full bg-violet-500 text-xs font-bold text-white">
              {totalItems}
            </span>
          )}
        </Button>
      </section>
    </header>
  );
};
