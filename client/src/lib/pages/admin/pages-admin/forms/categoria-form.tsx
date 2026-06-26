import { zodResolver } from '@hookform/resolvers/zod';
import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';

import { Button } from '@/components/ui/button';
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { useCategories } from '@/lib/hooks/use-categories';
import type { Category } from '@/lib/services/categories.service';

import {
  type CategoriaFormData,
  CategoriaValidationSchema,
} from './schemas/categoria-schema';

export function CategoriaForm({
  item,
  onClose,
}: {
  item: Category | null;
  onClose: () => void;
}) {
  const { createCategory, updateCategory, isPending } = useCategories();
  const form = useForm<CategoriaFormData>({
    resolver: zodResolver(CategoriaValidationSchema),
    mode: 'onChange',
    defaultValues: { descricao: item?.descricao ?? '' },
  });
  const { reset } = form;

  useEffect(() => {
    reset({ descricao: item?.descricao ?? '' });
  }, [item, reset]);

  function onSubmit(data: CategoriaFormData) {
    if (item) {
      updateCategory({ id: item.id, data }, { onSuccess: onClose });
    } else {
      createCategory(data, { onSuccess: onClose });
    }
  }

  return (
    <form
      className="flex flex-col gap-6"
      onSubmit={form.handleSubmit(onSubmit)}
    >
      <FieldGroup>
        <Controller
          name="descricao"
          control={form.control}
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>
                Nome da categoria <span className="text-red-400">*</span>
              </FieldLabel>
              <Input
                {...field}
                id="descricao"
                aria-invalid={fieldState.invalid}
              />
              {fieldState.invalid && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />
        <div className="flex gap-2">
          <Button
            type="submit"
            disabled={isPending}
            className="bg-violet-600 hover:bg-violet-500 text-white"
          >
            {isPending ? 'Salvando...' : item ? 'Salvar Alterações' : 'Inserir'}
          </Button>
          {item && (
            <Button type="button" variant="outline" onClick={onClose}>
              Cancelar
            </Button>
          )}
        </div>
      </FieldGroup>
    </form>
  );
}
