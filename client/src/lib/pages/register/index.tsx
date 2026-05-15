import { zodResolver } from '@hookform/resolvers/zod';
import { useMutation } from '@tanstack/react-query';
import { Link } from '@tanstack/react-router';
import { Controller, useForm } from 'react-hook-form';
import { toast } from 'sonner';
import type * as z from 'zod';

import { Button } from '@/components/ui/button';
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { api } from '@/lib/services/constants';

import { RegisterValidationSchema } from './schema';

const Register = () => {
  const RegisterForm = useForm({
    resolver: zodResolver(RegisterValidationSchema),
    mode: 'onChange',
    defaultValues: {
      name: '',
      address: '',
      email: '',
      login: '',
      password: '',
    },
  });

  type FormData = z.infer<typeof RegisterValidationSchema>;
  const registerMutation = useMutation({
    mutationFn: async (data: FormData) => {
      console.log(data);
      const response = await api.post('/register', data);
      return response.data;
    },

    onSuccess: (data) => {
      // provisório até rota de registro estar pronta
      console.log(data);
      toast('Login realizado!');
    },

    onError: (error) => {
      // provisório até rota de registro estar pronta
      console.log(error);
      toast('Login falhou');
    },
  });

  function onSubmit(data: FormData) {
    registerMutation.mutate(data);
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
          RegisterForm.handleSubmit(onSubmit)(e);
        }}
        className="mt-8 space-y-5"
      >
        <FieldGroup>
          {/* Campo nome */}
          <Controller
            name="name"
            control={RegisterForm.control}
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
            name="address"
            control={RegisterForm.control}
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
            control={RegisterForm.control}
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
          {/* Campo Username */}
          <Controller
            name="login"
            control={RegisterForm.control}
            render={({ field, fieldState }) => (
              <Field data-invalid={fieldState.invalid}>
                <FieldLabel htmlFor="form-username">
                  Nome de usuário <span className="text-destructive">*</span>
                </FieldLabel>
                <Input
                  {...field}
                  id="form-username"
                  type="text"
                  placeholder="Fulanim"
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
            name="password"
            control={RegisterForm.control}
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
            <Link to="/" className="block w-full text-center">
              <Button className="mt-4 w-48 rounded-md bg-indigo-500 font-semibold hover:bg-indigo-400">
                Voltar
              </Button>
            </Link>
            <Button
              disabled={registerMutation.isPending}
              type="submit"
              className="mt-4 w-48 rounded-md bg-indigo-500 py-2 font-semibold hover:bg-indigo-400"
            >
              {registerMutation.isPending ? 'Cadastrando...' : 'Cadastrar'}
            </Button>
          </Field>
        </FieldGroup>
      </form>
    </div>
  );
};

export default Register;
