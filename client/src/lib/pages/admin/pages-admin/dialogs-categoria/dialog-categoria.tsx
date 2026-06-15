import { Button } from '@base-ui/react/button';

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

function DialogCategoria() {
  return (
    <Dialog>
      <form>
        <DialogTrigger
          render={
            <Button className="h-12 w-full rounded-md bg-emerald-600 text-lg font-bold hover:bg-emerald-500">
              Inserir nova categoria
            </Button>
          }
        />
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Nova categoria</DialogTitle>
            <DialogDescription>
              Cadastrar nova categoria de produto
            </DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="nomeCategoria">Nome da categoria</Label>
              <Input id="nomeCategoria" name="nomeCategoria1" />
            </Field>
          </FieldGroup>
          <DialogFooter>
            <DialogClose>Cancelar</DialogClose>
            <Button type="submit">Inserir</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

export default DialogCategoria;
