import { Button } from '@base-ui/react/button';
import { Link, Link as LinkRouter } from '@tanstack/react-router';
import { ShoppingCart } from 'lucide-react';

import SearchHeader from '@/lib/components/search-header';

export const Header = () => {
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
          <Button className="mt-4 w-48 rounded-md bg-purple-700 font-bold hover:bg-purple-400">
            Login
          </Button>
        </Link>
        <Link to="/cadastrar" className="block w-full text-center">
          <Button className="mt-4 w-48 rounded-md bg-purple-700 font-bold hover:bg-purple-400">
            Cadastrar-se
          </Button>
        </Link>
        <Link to="/carrinho" className="block w-full text-center">
          <Button className="mt-4 w-48 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center gap-10">
            Carrinho
            <ShoppingCart />
          </Button>
        </Link>
      </div>
    </header>
  );
};
