import * as z from 'zod';

export const LoginValidationSchema = z.object({
  login: z
    .string()
    .min(5, 'Mínimo de 5 caracteres')
    .max(20, 'Máximo de 20 caracteres'),
  password: z
    .string()
    .min(8, 'Mínimo de 8 caracteres')
    .max(12, 'Máximo de 12 caracteres'),
});

export type LoginFormData = z.infer<typeof LoginValidationSchema>;
