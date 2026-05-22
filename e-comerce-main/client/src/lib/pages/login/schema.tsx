import * as z from 'zod';

export const LoginValidationSchema = z.object({
  email: z.email('Digite um email válido'),
  senha: z
    .string()
    .min(8, 'Mínimo de 8 caracteres')
    .max(12, 'Máximo de 12 caracteres'),
});
