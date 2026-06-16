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
        <DialogContent
          className="sm:max-w-sm  bg-[oklch(0.18_0.03_280/95%)]
            text-[oklch(0.97_0.01_280)]
            border-[oklch(0.45_0.06_290/25%)]"
        >
          <DialogHeader>
            <DialogTitle>Nova categoria</DialogTitle>
            <DialogDescription>
              Cadastrar nova categoria de produto
            </DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="descricao">Nome da categoria</Label>
              <Input id="descricao" name="descricao" />
            </Field>
          </FieldGroup>
          {/* "rounded-md border px-4 py-2 font-medium hover:bg-accent" */}
          <DialogFooter className="gap-6">
            <DialogClose className="rounded-md border px-4 py-2 font-medium hover:bg-white/10">
              Cancelar
            </DialogClose>
            <Button
              className="rounded-md bg-emerald-600 px-4 py-2 font-medium text-white transition-colors hover:bg-emerald-700"
              type="submit"
            >
              Inserir
            </Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

export default DialogCategoria;
