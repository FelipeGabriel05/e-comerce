import { LogOut } from 'lucide-react';

import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';

type LogoutDialogProps = {
  onConfirm: () => void;
  isPending?: boolean;
};

export const LogoutDialog = ({
  onConfirm,
  isPending = false,
}: LogoutDialogProps) => {
  return (
    <Dialog>
      <DialogTrigger>
        <Button className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-3">
          Desconectar
          <LogOut size={18} />
        </Button>
      </DialogTrigger>

      <DialogContent
        className="sm:max-w-sm bg-indigo-950"
        showCloseButton={false}
      >
        <DialogHeader className="flex flex-col items-center justify-center">
          <DialogTitle className="flex flex-col items-center justify-center text-white">
            <div className="flex h-14 w-14 items-center justify-center rounded-full bg-purple-500/20">
              <LogOut className="text-white" />
            </div>
            Desconectar
          </DialogTitle>
          <DialogDescription className="text-white align-center">
            Tem certeza que deseja encerrar sua sessão?
          </DialogDescription>
        </DialogHeader>

        <DialogFooter className="sm:justify-start flex gap-3">
          <DialogClose
            render={
              <button
                type="button"
                disabled={isPending}
                className="flex-1 rounded-lg border border-white/10 bg-white/5 py-2.5 text-sm font-semibold text-white transition hover:bg-white/10 disabled:opacity-50"
              >
                Cancelar
              </button>
            }
          />
          <button
            type="button"
            onClick={onConfirm}
            disabled={isPending}
            className="flex-1 rounded-lg bg-purple-700 py-2.5 text-sm font-semibold text-white transition hover:bg-purple-600 disabled:opacity-50"
          >
            {isPending ? 'Saindo...' : 'Sim, desconectar'}
          </button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};
