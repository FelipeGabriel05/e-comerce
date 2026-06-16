import { Button } from '@base-ui/react/button';
import { SquarePen } from 'lucide-react';

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
import { Field, FieldGroup } from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';

type DialogEditProps = {
  categoria: {
    id: number;
    descricao: string;
  };
};

function DialogEdit({ categoria }: DialogEditProps) {
  return (
    <Dialog>
      <DialogTrigger
        render={
          <Button className="flex h-10 w-10 items-center justify-center rounded-md bg-blue-600 hover:bg-blue-800">
            <SquarePen />
          </Button>
        }
      />
      <form>
        <DialogContent
          className="sm:max-w-sm  bg-[oklch(0.18_0.03_280/95%)]
            text-[oklch(0.97_0.01_280)]
            border-[oklch(0.45_0.06_290/25%)]"
        >
          <DialogHeader>
            <DialogTitle>Editar categoria</DialogTitle>
            <DialogDescription>Salva as novas alterações</DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="descricao">Nome da categoria</Label>
              <Input
                id="descricao"
                name="descricao"
                defaultValue={categoria.descricao}
              />
            </Field>
          </FieldGroup>
          <DialogFooter className="gap-6">
            <DialogClose className="rounded-md border px-4 py-2 font-medium hover:bg-white/10">
              Cancelar
            </DialogClose>
            <Button
              className="rounded-md bg-emerald-600 px-4 py-2 font-medium text-white transition-colors hover:bg-emerald-700"
              type="submit"
            >
              Salvar alterações
            </Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

export default DialogEdit;
