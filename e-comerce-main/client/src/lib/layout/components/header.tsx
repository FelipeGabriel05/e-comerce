import { useMutation } from '@tanstack/react-query';
import { Button } from '@base-ui/react/button';
import { Link, useNavigate } from '@tanstack/react-router';
import { LogIn, LogOut, ShoppingCart } from 'lucide-react';
import { useState } from 'react';
import { toast } from 'sonner';

import SearchHeader from '@/lib/layout/components/search-header';
import { logoutService } from '@/lib/services/auth.services';
import { authStore } from '@/lib/store/authStore';
import { useAuth } from '@/lib/store/useAuth';
import { LogoutDialog } from './LogoutDialog';

export const Header = () => {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [logoutOpen, setLogoutOpen] = useState(false);

  const logoutMutation = useMutation({
    mutationFn: logoutService,
    onSuccess: () => {
      authStore.clear();
      setLogoutOpen(false); // Fecha o modal após sucesso
      navigate({ to: '/' });
      toast('Sessão encerrada.');
    },
    onError: () => {
      authStore.clear();
      setLogoutOpen(false); // Fecha o modal mesmo se der erro no servidor
      navigate({ to: '/' });
    },
  });

  return (
    <>
      <header className="flex items-center justify-between px-8 py-4 border-b">
        {/* Logo */}
        <div>
          <h1 className="text-2xl font-bold">
            <Link to="/">Logo</Link>
          </h1>
        </div>

        {/* Busca */}
        <div className="w-full max-w-md">
          <SearchHeader />
        </div>

        {/* Ações */}
        <div className="flex items-center gap-4">
          {isAuthenticated ? (
            <Button
              onClick={() => setLogoutOpen(true)}
              className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-3 text-white"
            >
              Desconectar
              <LogOut size={18} />
            </Button>
          ) : (
            <>
              {/* Transformando o Link no botão visualmente */}
              <Link
                to="/login"
                className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-5 text-white transition-colors"
              >
                Login
                <LogIn />
              </Link>
              
              <Link
                to="/carrinho"
                className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-5 text-white transition-colors"
              >
                Carrinho
                <ShoppingCart />
              </Link>
            </>
          )}
        </div>
      </header>

      <LogoutDialog
        open={logoutOpen}
        onConfirm={() => logoutMutation.mutate()}
        onCancel={() => setLogoutOpen(false)}
        isPending={logoutMutation.isPending}
      />
    </>
  );
};