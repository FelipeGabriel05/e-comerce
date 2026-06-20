import { Button } from '@base-ui/react/button';
import { zodResolver } from '@hookform/resolvers/zod';
import { Controller, useForm } from 'react-hook-form';

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
import { Field, FieldError, FieldGroup } from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { useCategories } from '@/lib/hooks/use-categories';
import { useUserProfile } from '@/lib/hooks/use-user-profile';

import type { CategoriaFormData } from './schemas/categoria-schema';
import { CategoriaValidationSchema } from './schemas/categoria-schema';

function DialogCategoria() {
  const user = useUserProfile();
  const { createCategory, isPending } = useCategories();
  const InsertCategoriaForm = useForm({
    resolver: zodResolver(CategoriaValidationSchema),
    mode: 'onChange',
    defaultValues: {
      descricao: '',
    },
  });

  function onSubmit(data: CategoriaFormData) {
    if (!user) return null;
    createCategory(data);
  }
  return (
    <Dialog>
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
          border-[oklch(0.45_0.06_290/25%)] "
      >
        <form
          className="flex flex-col gap-6"
          onSubmit={(e) => {
            InsertCategoriaForm.handleSubmit(onSubmit)(e);
          }}
        >
          <DialogHeader>
            <DialogTitle>Nova categoria</DialogTitle>
            <DialogDescription>
              Cadastrar nova categoria de produto
            </DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Controller
              name="descricao"
              control={InsertCategoriaForm.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <Label htmlFor="descricao">Nome da categoria</Label>
                  <Input
                    {...field}
                    id="descricao"
                    name="descricao"
                    required
                    aria-invalid={fieldState.invalid}
                  />
                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            />
          </FieldGroup>
          <DialogFooter className="gap-6">
            <DialogClose className="rounded-md border px-4 py-2 font-medium hover:bg-white/10">
              Cancelar
            </DialogClose>
            <Button
              className="rounded-md bg-emerald-600 px-4 py-2 font-medium text-white transition-colors hover:bg-emerald-700"
              type="submit"
            >
              {isPending ? 'Inserindo...' : 'Inserir'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}

export default DialogCategoria;
