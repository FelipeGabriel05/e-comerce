import { Button } from '@base-ui/react/button';
import { Link, Link as LinkRouter, useNavigate } from '@tanstack/react-router';
import { LogIn, LogOut, ShoppingCart } from 'lucide-react';
import { useState } from 'react';
import { toast } from 'sonner';

import SearchHeader from '@/lib/components/search-header';
import { authStore } from '@/lib/store/authStore';
import { useAuth } from '@/lib/store/useAuth';
import { LogoutDialog } from './LogoutDialog';

const Header = () => {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [logoutOpen, setLogoutOpen] = useState(false);

  function handleLogout() {
    authStore.clear(); // limpa store + localStorage
    setLogoutOpen(false);
    navigate({ to: '/' });
    toast('Sessão encerrada.');
  }

  return (
    <>
      <header className="flex items-center justify-between px-8 py-4 border-b">
        {/* Logo */}
        <div>
          <h1 className="text-2xl font-bold">
            <LinkRouter to="/">Logo</LinkRouter>
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
              className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-3"
            >
              Desconectar
              <LogOut size={18} />
            </Button>
          ) : (
            <>
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
            </>
          )}
        </div>
      </header>

      <LogoutDialog
        open={logoutOpen}
        onConfirm={handleLogout}
        onCancel={() => setLogoutOpen(false)}
      />
    </>
  );
};

export { Header };
