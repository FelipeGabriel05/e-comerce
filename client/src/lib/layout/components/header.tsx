import { Button } from '@base-ui/react/button';
import { Link, Link as LinkRouter, useLocation } from '@tanstack/react-router';
import { LogIn, ShoppingCart } from 'lucide-react';

import SearchHeader from '@/lib/components/search-header';

export const Header = () => {
  const location = useLocation();

  const isAdmin = location.pathname.startsWith('/admin');

  if (isAdmin) {
    return null;
  }

  return (
    <header className="flex items-center justify-between px-8 py-4 border-b">
      <div>
        <h1 className="text-2xl font-bold">
          <LinkRouter to="/">Logo</LinkRouter>
        </h1>
      </div>

      <div className="w-full max-w-md">
        <SearchHeader />
      </div>

      <div className="flex items-center gap-10">
        <Link to="/login" className="block w-full text-center">
          <Button className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-5">
            Login
            <LogIn />
          </Button>
        </Link>
        <Link to="/carrinho" className="block w-full text-center">
          <Button className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-5">
            Carrinho
            <ShoppingCart />
          </Button>
        </Link>
      </div>
    </header>
  );
};
