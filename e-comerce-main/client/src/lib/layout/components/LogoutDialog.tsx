export type LogoutDialogProps = {
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
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm"
      onClick={onCancel}
    >
      <div
        className="w-full max-w-sm rounded-2xl bg-[#1e1b4b] p-8 shadow-2xl border border-white/10"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Ícone */}
        <div className="mb-4 flex justify-center">
          <div className="flex h-14 w-14 items-center justify-center rounded-full bg-purple-500/20">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-7 w-7 text-purple-400"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              strokeWidth={2}
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a2 2 0 01-2 2H5a2 2 0 01-2-2V7a2 2 0 012-2h6a2 2 0 012 2v1"
              />
            </svg>
          </div>
        </div>

        <h3 className="mb-2 text-center text-xl font-bold text-white">
          Desconectar
        </h3>
        <p className="mb-8 text-center text-sm text-gray-400">
          Tem certeza que deseja encerrar sua sessão?
        </p>

        <div className="flex gap-3">
          <button
            type="button"
            onClick={onCancel}
            disabled={isPending}
            className="flex-1 rounded-lg border border-white/10 bg-white/5 py-2.5 text-sm font-semibold text-white transition hover:bg-white/10 disabled:opacity-50"
          >
            Cancelar
          </button>
          <button
            type="button"
            onClick={onConfirm}
            disabled={isPending}
            className="flex-1 rounded-lg bg-purple-700 py-2.5 text-sm font-semibold text-white transition hover:bg-purple-600 disabled:opacity-50"
          >
            {isPending ? 'Saindo...' : 'Sim, desconectar'}
          </button>
        </div>
      </div>
    </div>
  );
};