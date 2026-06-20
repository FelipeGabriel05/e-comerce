import { Button } from '@base-ui/react/button';
import { zodResolver } from '@hookform/resolvers/zod';
import { SquarePen } from 'lucide-react';
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
import type { DialogEditProps } from '@/lib/services/categories.service';

import type { CategoriaFormData } from './schemas/categoria-schema';
import { CategoriaValidationSchema } from './schemas/categoria-schema';

function DialogEdit({ categoria }: DialogEditProps) {
  const user = useUserProfile();
  const { updateCategory, isPending } = useCategories();
  const UpdateCategoriaForm = useForm({
    resolver: zodResolver(CategoriaValidationSchema),
    mode: 'onChange',
    defaultValues: {
      descricao: '',
    },
  });

  function onSubmit(data: CategoriaFormData) {
    if (!user) return null;

    updateCategory({
      id: categoria.id,
      data: data,
    });
  }
  return (
    <Dialog>
      <DialogTrigger
        render={
          <Button className="flex h-10 w-10 items-center justify-center rounded-md bg-blue-600 hover:bg-blue-800">
            <SquarePen />
          </Button>
        }
      />
      <DialogContent
        className="sm:max-w-sm  bg-[oklch(0.18_0.03_280/95%)]
          text-[oklch(0.97_0.01_280)]
          border-[oklch(0.45_0.06_290/25%)]"
      >
        <form
          className="flex flex-col gap-6"
          onSubmit={(e) => {
            e.preventDefault();
            UpdateCategoriaForm.handleSubmit(onSubmit)(e);
          }}
        >
          <DialogHeader>
            <DialogTitle>Editar categoria</DialogTitle>
            <DialogDescription>Salva as novas alterações</DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Controller
              name="descricao"
              control={UpdateCategoriaForm.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <Label htmlFor="descricao">Nome da categoria</Label>
                  <Input
                    {...field}
                    id="descricao"
                    name="descricao"
                    required
                    defaultValue={categoria.descricao}
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
              {isPending ? 'Salvando alterações ...' : 'Salvar alterações'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}

export default DialogEdit;
