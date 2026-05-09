import * as z from 'zod';

export const RegisterValidationSchema = z.object({
  nome: z
    .string()
    .min(5, 'O nome deve ter no mínimo 5 caracteres')
    .max(80, 'O nome deve ter no máximo 80 caracteres'),
  username: z
    .string()
    .min(5, 'O username deve ter no mínimo 5 caracteres')
    .max(20, 'O username deve ter no máximo 20 caracteres'),
  endereco: z
    .string()
    .min(10, 'Informe um endereço mais completo')
    .max(120, 'Endereço muito longo'),
  email: z.email('Digite um email válido'),
  senha: z
    .string()
    .min(8, 'Mínimo de 8 caracteres')
    .max(12, 'Máximo de 12 caracteres')
    .regex(/[A-Z]/, 'Sua senha deve conter pelo menos uma letra maiúscula')
    .regex(/[a-z]/, 'Sua senha deve conter pelo menos uma letra minúscula')
    .regex(/[0-9]/, 'Sua senha deve conter pelo menos um número')
    .regex(
      /[^A-Za-z0-9]/,
      'Sua senha deve conter pelo menos um caractere especial',
    ),
});
