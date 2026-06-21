import * as z from 'zod';

const MAX_FILE_SIZE = 5 * 1024 * 1024;

export const ProductValidationSchema = z.object({
  descricao: z.string().min(1, 'Descrição é obrigatória'),
  preco: z.coerce
    .number()
    .min(0, 'Preço deve ser maior ou igual a 0')
    .multipleOf(0.01, 'Máximo 2 casas decimais'),
  foto: z
    .union([
      z.string().url('URL da foto inválida'),
      z.literal(''),
      z
        .instanceof(File)
        .refine((file) => file.type.startsWith('image/'), {
          message: 'O arquivo deve ser uma imagem',
        })
        .refine((file) => file.size <= MAX_FILE_SIZE, {
          message: 'A imagem deve ter no máximo 5MB',
        }),
    ])
    .default(''),
  quantidade: z.coerce
    .number()
    .min(0, 'Quantidade deve ser maior ou igual a 0'),
  categoriaId: z.coerce.number(),
});

export type ProductFormData = z.infer<typeof ProductValidationSchema>;
