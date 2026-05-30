import { Link as LinkRouter } from '@tanstack/react-router';
import { LogIn, ShoppingCart } from 'lucide-react';

import { Button } from '@/components/ui/button';
import SearchHeader from '@/lib/components/search-header';
import { useCart } from '@/lib/hooks/use-cart';

export const Header = () => {
  const { totalItems } = useCart();

  return (
    <header className="sticky top-0 z-10 w-full bg-base-100/80 backdrop-blur-md flex items-center justify-between px-8 py-4 border-b">
      <div>
        <h1 className="text-2xl font-bold text-white">
          <LinkRouter to="/">Games Store</LinkRouter>
        </h1>
      </div>

      <div className="w-full max-w-md">
        <SearchHeader />
      </div>

      <div className="flex items-center gap-6">
        <LinkRouter to="/login">
          <Button className="h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-600 text-white flex justify-center items-center gap-2 px-4">
            Login
            <LogIn size={16} />
          </Button>
        </LinkRouter>

        <LinkRouter to="/carrinho" className="relative">
          <Button className="h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-600 text-white flex justify-center items-center gap-2 px-4">
            <ShoppingCart size={16} />
            Carrinho
            {totalItems > 0 && (
              <span className="absolute -right-2 -top-2 flex h-5 w-5 items-center justify-center rounded-full bg-violet-500 text-xs font-bold text-white">
                {totalItems}
              </span>
            )}
          </Button>
        </LinkRouter>
      </div>
    </header>
  );
};
