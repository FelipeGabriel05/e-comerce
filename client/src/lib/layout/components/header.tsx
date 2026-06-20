import { Button } from '@base-ui/react/button';
import { Link, Link as LinkRouter } from '@tanstack/react-router';
import { LogIn, ShoppingCart } from 'lucide-react';
import { useState } from 'react';

import SearchHeader from '@/lib/components/search-header';
import { useAuth } from '@/lib/hooks/use-auth';
import { LogoutDialog } from '@/lib/layout/components/logout-dialog';
import { UserMenuDropdown } from '@/lib/layout/components/user-menu-dropdown';

export const Header = () => {
  const { isAuthenticated, logout, isLogoutPending } = useAuth() as {
    isAuthenticated: boolean;
    logout: () => void;
    isLogoutPending: boolean;
  };
  const [logoutOpen, setLogoutOpen] = useState(false);

  function handleLogoutConfirm() {
    logout();
    setLogoutOpen(false);
  }

  return (
    <>
      <header className="flex items-center justify-between px-8 py-4 border-b">
        <div>
          <h1 className="text-2xl font-bold">
            <LinkRouter to="/">Logo</LinkRouter>
          </h1>
        </div>

        <div className="w-full max-w-md">
          <SearchHeader />
        </div>

        <div className="flex items-center gap-4">
          {isAuthenticated ? (
            <UserMenuDropdown onRequestLogout={() => setLogoutOpen(true)} />
          ) : (
            <>
              <Button
                render={<Link to="/login" />}
                className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-5"
              >
                Login
                <LogIn />
              </Button>
              <Button
                render={<Link to="/carrinho" />}
                className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-5"
              >
                Carrinho
                <ShoppingCart />
              </Button>
            </>
          )}
        </div>
      </header>

      <LogoutDialog
        open={logoutOpen}
        onConfirm={handleLogoutConfirm}
        onCancel={() => setLogoutOpen(false)}
        isPending={isLogoutPending}
      />
    </>
  );
};
