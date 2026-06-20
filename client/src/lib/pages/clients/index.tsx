import { Link } from '@tanstack/react-router';
import { ClipboardList, LogOut, Settings, User } from 'lucide-react';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { ConfirmDialog } from '@/components/ui/confirm-dialog';
import { useUserProfile } from '@/lib/hooks/use-user-profile';

const MainClientPage = () => {
  const { user, logout, isLogoutPending } = useUserProfile();
  const [confirmOpen, setConfirmOpen] = useState(false);

  return (
    <div className="flex min-h-[calc(100vh-5rem)] items-start justify-center p-8">
      <div className="w-full max-w-md space-y-6">
        <div className="flex flex-col items-center gap-3 rounded-xl border border-white/10 bg-white/5 backdrop-blur-sm p-6 text-center">
          <div className="flex h-16 w-16 items-center justify-center rounded-full bg-purple-700/80">
            <User size={32} className="text-white" />
          </div>
          <div>
            <h1 className="text-xl font-bold text-white">
              {user?.name ?? '—'}
            </h1>
            <p className="text-sm text-white/50">@{user?.login}</p>
          </div>
          {user?.email && <p className="text-sm text-white/50">{user.email}</p>}
        </div>

        <nav className="space-y-2">
          <Link
            to="/dadosCliente"
            className="flex items-center gap-3 rounded-xl border border-white/10 bg-white/5 backdrop-blur-sm px-4 py-3 text-sm font-medium text-white transition-all hover:border-white/25 hover:bg-white/10"
          >
            <Settings size={18} className="text-violet-400" />
            Meus dados
          </Link>

          <Link
            to="/historicoCliente"
            className="flex items-center gap-3 rounded-xl border border-white/10 bg-white/5 backdrop-blur-sm px-4 py-3 text-sm font-medium text-white transition-all hover:border-white/25 hover:bg-white/10"
          >
            <ClipboardList size={18} className="text-violet-400" />
            Histórico de Pedidos
          </Link>
        </nav>

        <Button
          variant="destructive"
          className="w-full"
          onClick={() => setConfirmOpen(true)}
          disabled={isLogoutPending}
        >
          <LogOut size={16} />
          {isLogoutPending ? 'Saindo...' : 'Sair'}
        </Button>

        {confirmOpen && (
          <ConfirmDialog
            title="Sair da conta"
            description="Tem certeza que deseja encerrar a sessão?"
            confirmLabel="Sair"
            onConfirm={() => {
              setConfirmOpen(false);
              logout();
            }}
            onCancel={() => setConfirmOpen(false)}
          />
        )}
      </div>
    </div>
  );
};

export default MainClientPage;
