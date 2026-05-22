import { Button } from '@base-ui/react/button';
import { useMutation } from '@tanstack/react-query';
import { Link, Link as LinkRouter, useNavigate } from '@tanstack/react-router';
import { LogIn, LogOut, ShoppingCart } from 'lucide-react';
import { toast } from 'sonner';

import SearchHeader from '@/lib/components/search-header';
import { logoutService } from '@/lib/services/auth.services';

export const Header = () => {
  const navigate = useNavigate();

  const isLoggedIn = document.cookie.includes('session_token');

  const logoutMutation = useMutation({
    mutationFn: logoutService,
    onSuccess: () => {
      toast('Logout realizado!');
      navigate({ to: '/login' });
    },
    onError: () => {
      toast('Erro ao fazer logout');
    },
  });

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
        {isLoggedIn ? (
          <Button
            onClick={() => logoutMutation.mutate()}
            disabled={logoutMutation.isPending}
            className="w-48 h-10 rounded-md bg-red-600 font-bold hover:bg-red-400 flex justify-center items-center gap-5"
          >
            {logoutMutation.isPending ? 'Saindo...' : 'Logout'}
            <LogOut />
          </Button>
        ) : (
          <Link to="/login" className="block w-full text-center">
            <Button className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-5">
              Login
              <LogIn />
            </Button>
          </Link>
        )}

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
