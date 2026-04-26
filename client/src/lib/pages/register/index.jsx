import { zodResolver } from '@hookform/resolvers/zod';
import { Link } from '@tanstack/react-router';
import { Controller, useForm } from 'react-hook-form';
import { toast } from 'sonner';
import * as z from 'zod';

import { Button } from '@/components/ui/button';
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';

const Register = () => {
  const formSchema = z.object({
    nome: z
      .string()
      .min(5, 'O nome deve ter no mínimo 5 caracteres')
      .max(80, 'O nome deve ter no máximo 80 caracteres'),
    endereco: z
      .string()
      .min(10, 'Informe um endereço mais completo')
      .max(120, 'Endereço muito longo'),
    email: z.string().email('Digite um email válido'),
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

  const form = useForm({
    resolver: zodResolver(formSchema),
    mode: 'onChange',
    defaultValues: {
      nome: '',
      endereco: '',
      email: '',
      senha: '',
    },
  });

  function onSubmit(data) {
    console.log(data);
    alert('Cadastro realizado com sucesso');
    toast('Cadastro realizado!', {
      description: JSON.stringify(data, null, 2),
    });
  }

  return (
    <div className="bg-formblack text-white pt-6 px-12 pb-12 flex min-h-full w-125 flex-col justify-center rounded-2xl shadow-lg mx-auto">
      <div className="text-center">
        <img
          src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=500"
          alt="Your Company"
          className="mx-auto h-10 w-auto"
        />
        <h2 className="mt-4 text-2xl font-bold tracking-tight">
          Formulário de Cadastro
        </h2>
      </div>

      <form
        onSubmit={(e) => {
          e.preventDefault();
          form.handleSubmit(onSubmit)(e);
        }}
        className="mt-8 space-y-5"
      >
        <FieldGroup>
          {/* Campo nome */}
          <Controller
            name="nome"
            control={form.control}
            render={({ field, fieldState }) => (
              <Field data-invalid={fieldState.invalid}>
                <FieldLabel htmlFor="form-name">
                  Nome <span className="text-destructive">*</span>
                </FieldLabel>
                <Input
                  {...field}
                  id="form-name"
                  type="text"
                  placeholder="Nome completo"
                  aria-invalid={fieldState.invalid}
                  required
                />
                {fieldState.invalid && (
                  <FieldError errors={[fieldState.error]} />
                )}
              </Field>
            )}
          />
          {/* Campo Endereço */}
          <Controller
            name="endereco"
            control={form.control}
            render={({ field, fieldState }) => (
              <Field data-invalid={fieldState.invalid}>
                <FieldLabel htmlFor="form-address">
                  Endereço <span className="text-destructive">*</span>
                </FieldLabel>
                <Input
                  {...field}
                  id="form-address"
                  type="text"
                  placeholder="rua fulano, nome do bairro, 123"
                  aria-invalid={fieldState.invalid}
                  required
                />
                {fieldState.invalid && (
                  <FieldError errors={[fieldState.error]} />
                )}
              </Field>
            )}
          />
          {/* Campo email */}
          <Controller
            name="email"
            control={form.control}
            render={({ field, fieldState }) => (
              <Field data-invalid={fieldState.invalid}>
                <FieldLabel htmlFor="form-email">
                  Email <span className="text-destructive">*</span>
                </FieldLabel>
                <Input
                  {...field}
                  id="form-email"
                  type="email"
                  placeholder="nome@exemplo.com"
                  aria-invalid={fieldState.invalid}
                  required
                />
                {fieldState.invalid && (
                  <FieldError errors={[fieldState.error]} />
                )}
              </Field>
            )}
          />
          {/* Campo Senha */}
          <Controller
            name="senha"
            control={form.control}
            render={({ field, fieldState }) => (
              <Field data-invalid={fieldState.invalid}>
                <FieldLabel htmlFor="form-password">
                  Senha <span className="text-destructive">*</span>
                </FieldLabel>
                <Input
                  {...field}
                  id="form-password"
                  type="password"
                  placeholder="senha de no mínimo 8 caracteres"
                  aria-invalid={fieldState.invalid}
                  required
                />
                {fieldState.invalid && (
                  <FieldError errors={[fieldState.error]} />
                )}
              </Field>
            )}
          />
          <Field orientation="horizontal">
            <Button
              asChild
              className="mt-4 w-48 rounded-md bg-indigo-500 font-semibold hover:bg-indigo-400"
            >
              <Link to="/" className="block w-full text-center">
                Voltar
              </Link>
            </Button>
            <Button
              type="submit"
              className="mt-4 w-48 rounded-md bg-indigo-500 py-2 font-semibold hover:bg-indigo-400"
            >
              Cadastrar
            </Button>
          </Field>
        </FieldGroup>
      </form>
    </div>
  );
};

export default Register;
