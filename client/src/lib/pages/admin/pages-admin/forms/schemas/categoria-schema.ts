import * as z from 'zod';

export const CategoriaValidationSchema = z.object({
  descricao: z
    .string()
    .min(3, 'Mínimo de 3 caracteres')
    .max(30, 'Máximo de 30 caracteres'),
});

export type CategoriaFormData = z.infer<typeof CategoriaValidationSchema>;
