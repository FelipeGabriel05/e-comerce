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
    NomeCategoria: string;
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
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Editar categoria</DialogTitle>
            <DialogDescription>Salva as novas alterações</DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="nomeCategoria">Nome da categoria</Label>
              <Input
                id="nomeCategoria"
                name="nomeCategoria1"
                defaultValue={categoria.NomeCategoria}
              />
            </Field>
          </FieldGroup>
          <DialogFooter>
            <DialogClose>
              <Button>Cancelar</Button>
            </DialogClose>
            <Button type="submit">Salvar alteração</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

export default DialogEdit;
