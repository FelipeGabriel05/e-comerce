import * as z from 'zod';

export const ProductValidationSchema = z.object({
  descricao: z.string().min(1, 'Descrição é obrigatória'),
  preco: z.coerce.number().min(0, 'Preço deve ser maior que 0'),
  foto: z.string().default(''),
  quantidade: z.coerce.number().min(0, 'Quantidade deve ser maior que 0'),
  categoriaId: z.coerce.number().default(1),
});

export type ProductFormData = z.infer<typeof ProductValidationSchema>;
