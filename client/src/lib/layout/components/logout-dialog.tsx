import { LogOut } from 'lucide-react';

import { Button } from '@/components/ui/button';

type LogoutDialogProps = {
  open: boolean;
  onConfirm: () => void;
  onCancel: () => void;
  isPending?: boolean;
};

export const LogoutDialog = ({
  open,
  onConfirm,
  onCancel,
  isPending = false,
}: LogoutDialogProps) => {
  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      <button
        type="button"
        aria-label="Fechar dialog"
        onClick={onCancel}
        className="absolute inset-0 bg-black/60 backdrop-blur-sm cursor-default"
      />
      <div className="relative w-full max-w-sm rounded-2xl bg-[#8B00FF] p-8 shadow-2xl border border-white/10">
        <div className="mb-4 flex justify-center">
          <div className="flex h-14 w-14 items-center justify-center rounded-full bg-purple-500/20">
            <LogOut className="h-7 w-7 text-purple-400" />
          </div>
        </div>
        <h3 className="mb-2 text-center text-xl font-bold text-white">
          Desconectar
        </h3>
        <p className="mb-8 text-center text-sm text-gray-400">
          Tem certeza que deseja encerrar sua sessão?
        </p>
        <div className="flex gap-3">
          <Button
            variant="outline"
            onClick={onCancel}
            disabled={isPending}
            className="flex-1 border-white/10 bg-white/5 text-white hover:bg-white/10 hover:text-white"
          >
            Cancelar
          </Button>
          <Button
            onClick={onConfirm}
            disabled={isPending}
            className="flex-1 bg-purple-700 hover:bg-purple-600"
          >
            {isPending ? 'Saindo...' : 'Sim, desconectar'}
          </Button>
        </div>
      </div>
    </div>
  );
};
