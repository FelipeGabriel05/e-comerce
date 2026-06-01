import * as z from 'zod';

export const RegisterEditionSchema = z.object({
  name: z
    .string()
    .min(5, 'O nome deve ter no mínimo 5 caracteres')
    .max(80, 'O nome deve ter no máximo 80 caracteres'),
  login: z
    .string()
    .min(5, 'O username deve ter no mínimo 5 caracteres')
    .max(20, 'O username deve ter no máximo 20 caracteres'),
  address: z
    .string()
    .min(10, 'Informe um endereço mais completo')
    .max(120, 'Endereço muito longo'),
  email: z.email('Digite um email válido'),
  password: z
    .string()
    .min(8)
    .max(12)
    .regex(/[A-Z]/)
    .regex(/[a-z]/)
    .regex(/[0-9]/)
    .regex(/[^A-Za-z0-9]/)
    .optional()
    .or(z.literal(''))
    .transform((value: string | undefined | null) => value || null),
});

export type RegisterFormDataEdit = z.infer<typeof RegisterEditionSchema>;
export type RegisterFormDataInput = z.input<typeof RegisterEditionSchema>;
