import { Button } from '@base-ui/react/button';
import { Trash } from 'lucide-react';

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

type DialogDeleteProps = {
  categoria: {
    id: number;
    NomeCategoria: string;
  };
};

function DialogDelete({ categoria }: DialogDeleteProps) {
  return (
    <Dialog>
      <DialogTrigger
        render={
          <Button className="flex h-10 w-10 items-center justify-center rounded-md bg-red-600 hover:bg-red-800">
            <Trash />
          </Button>
        }
      />
      <form>
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Excluir categoria</DialogTitle>
            <DialogDescription>Salva as novas alterações</DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="nomeCategoria">
                Tem certeza que deseja excluir?
              </Label>
              <Input
                id="nomeCategoria"
                name="nomeCategoria1"
                defaultValue={categoria.NomeCategoria}
                readOnly
              />
            </Field>
          </FieldGroup>
          <DialogFooter>
            <DialogClose>
              <Button>Cancelar</Button>
            </DialogClose>
            <Button type="submit">Excluir</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

export default DialogDelete;
