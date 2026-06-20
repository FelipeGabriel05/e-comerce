import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';

type ConfirmDialogProps = {
  title: string;
  description: React.ReactNode;
  onConfirm: () => void;
  onCancel: () => void;
  confirmLabel?: string;
};

export const ConfirmDialog = ({
  title,
  description,
  onConfirm,
  onCancel,
  confirmLabel = 'Confirmar',
}: ConfirmDialogProps) => (
  <Dialog open onOpenChange={(open) => !open && onCancel()}>
    <DialogContent
      showCloseButton={false}
      className="sm:max-w-sm bg-[oklch(0.18_0.03_280/95%)] text-[oklch(0.97_0.01_280)] border-[oklch(0.45_0.06_290/25%)]"
    >
      <DialogHeader>
        <DialogTitle>{title}</DialogTitle>
        <DialogDescription>{description}</DialogDescription>
      </DialogHeader>
      <DialogFooter>
        <DialogClose
          render={
            <Button
              variant="outline"
              className="border-white/20 bg-transparent text-white hover:bg-white/10 hover:text-white"
              onClick={onCancel}
            />
          }
        >
          Cancelar
        </DialogClose>
        <Button variant="destructive" onClick={onConfirm}>
          {confirmLabel}
        </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
);
