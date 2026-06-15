import { zodResolver } from '@hookform/resolvers/zod';
import type { Resolver } from 'react-hook-form';
import { Controller, useForm } from 'react-hook-form';

import { Button } from '@/components/ui/button';
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import type { ProductFormData } from '@/lib/pages/admin/products/product-validation-schema';
import { ProductValidationSchema } from '@/lib/pages/admin/products/product-validation-schema';

type ProductFormProps = {
  defaultValues?: ProductFormData;
  onSubmit: (data: ProductFormData) => void;
  onCancel?: () => void;
  isEditing?: boolean;
  isPending?: boolean;
};

export const ProductForm = ({
  defaultValues,
  onSubmit,
  onCancel,
  isEditing = false,
  isPending = false,
}: ProductFormProps) => {
  const form = useForm<ProductFormData>({
    resolver: zodResolver(ProductValidationSchema) as Resolver<ProductFormData>,
    mode: 'onChange',
    defaultValues: defaultValues ?? {
      descricao: '',
      preco: 0,
      foto: '',
      quantidade: 0,
      categoriaId: 1,
    },
  });

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        form.handleSubmit(onSubmit)(e);
      }}
      className="flex flex-col gap-3"
    >
      <FieldGroup>
        <Controller
          name="descricao"
          control={form.control}
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>Descrição</FieldLabel>
              <Input {...field} aria-invalid={fieldState.invalid} />
              {fieldState.invalid && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />
        <Controller
          name="preco"
          control={form.control}
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>Preço</FieldLabel>
              <Input
                {...field}
                type="number"
                aria-invalid={fieldState.invalid}
              />
              {fieldState.invalid && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />
        <Controller
          name="quantidade"
          control={form.control}
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>Quantidade</FieldLabel>
              <Input
                {...field}
                type="number"
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
            {isEditing ? 'Salvar Alterações' : 'Criar Produto'}
          </Button>
          {isEditing && (
            <Button type="button" variant="outline" onClick={onCancel}>
              Cancelar
            </Button>
          )}
        </div>
      </FieldGroup>
    </form>
  );
};
