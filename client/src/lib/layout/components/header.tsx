import { Link as LinkRouter } from '@tanstack/react-router';
import { LogIn, Settings, ShoppingCart, User } from 'lucide-react';

import { buttonVariants } from '@/components/ui/button';
import SearchHeader from '@/lib/components/search-header';
import { useCart } from '@/lib/hooks/use-cart';
import { useUserProfile } from '@/lib/hooks/use-user-profile';
import { cn } from '@/lib/utils';

export const Header = () => {
  const { total } = useCart();
  const { user } = useUserProfile();

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
        {user?.administrador ? (
          <LinkRouter
            to="/admin"
            className={cn(
              buttonVariants(),
              'bg-purple-700 font-bold hover:bg-purple-600 text-white gap-2 px-4',
            )}
          >
            <Settings size={16} />
            Admin
          </LinkRouter>
        ) : user ? (
          <LinkRouter
            to="/cliente"
            className={cn(
              buttonVariants(),
              'bg-purple-700 font-bold hover:bg-purple-600 text-white gap-2 px-4',
            )}
          >
            <User size={16} />
            Perfil
          </LinkRouter>
        ) : (
          <LinkRouter
            to="/login"
            className={cn(
              buttonVariants(),
              'bg-purple-700 font-bold hover:bg-purple-600 text-white gap-2 px-4',
            )}
          >
            <LogIn size={16} />
            Login
          </LinkRouter>
        )}

        <LinkRouter
          to="/carrinho"
          className={cn(
            buttonVariants(),
            'relative bg-purple-700 font-bold hover:bg-purple-600 text-white gap-2 px-4',
          )}
        >
          <ShoppingCart size={16} />
          Carrinho
          {total > 0 && (
            <span className="absolute -right-2 -top-2 flex h-5 w-5 items-center justify-center rounded-full bg-violet-500 text-xs font-bold text-white">
              {total}
            </span>
          )}
        </LinkRouter>
      </div>
    </header>
  );
};
