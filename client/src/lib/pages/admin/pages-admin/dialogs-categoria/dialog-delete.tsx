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
import { useCategories } from '@/lib/hooks/use-categories';

type DialogDeleteProps = {
  categoria: {
    id: number;
    descricao: string;
  };
};

function DialogDelete({ categoria }: DialogDeleteProps) {
  const { deleteCategory } = useCategories();
  function handleDeleteCategory() {
    deleteCategory(categoria.id);
  }
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
        <DialogContent
          className="sm:max-w-sm  bg-[oklch(0.18_0.03_280/95%)]
            text-[oklch(0.97_0.01_280)]
            border-[oklch(0.45_0.06_290/25%)] "
        >
          <DialogHeader>
            <DialogTitle>Excluir categoria</DialogTitle>
            <DialogDescription>Salva as novas alterações</DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="descricao">Tem certeza que deseja excluir?</Label>
              <Input
                id="descricao"
                name="descricao"
                defaultValue={categoria.descricao}
                readOnly
              />
            </Field>
          </FieldGroup>
          <DialogFooter className="gap-6">
            <DialogClose className="rounded-md border px-4 py-2 font-medium hover:bg-white/10">
              Cancelar
            </DialogClose>
            <Button
              className="rounded-md bg-red-600 px-4 py-2 font-medium text-white transition-colors hover:bg-red-700"
              type="submit"
              onClick={handleDeleteCategory}
            >
              Excluir
            </Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

export default DialogDelete;
