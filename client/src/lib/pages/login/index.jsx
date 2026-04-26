import { zodResolver } from '@hookform/resolvers/zod';
import { Link } from '@tanstack/react-router';
import { Controller, useForm } from 'react-hook-form';
import * as z from 'zod';

import { Button } from '@/components/ui/button';
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';

const Login = () => {
  const formSchema = z.object({
    email: z.string().email('Digite um email válido'),
    senha: z
      .string()
      .min(8, 'Mínimo de 8 caracteres')
      .max(12, 'Máximo de 12 caracteres'),
  });

  const form = useForm({
    resolver: zodResolver(formSchema),
    mode: 'onchange',
    defaultValues: {
      email: '',
      senha: '',
    },
  });

  function onSubmit(data) {
    console.log(data);
    alert('dados enviados');
  }

  return (
    <div className="bg-formblack text-white pt-6 px-6 pb-12 flex min-h-full w-125 flex-col justify-center rounded-2xl py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <img
          src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=500"
          alt="Your Company"
          className="mx-auto h-10 w-auto"
        />
        <h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight text-white">
          Login
        </h2>
      </div>
      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form
          onSubmit={(e) => {
            e.preventDefault();
            form.handleSubmit(onSubmit)(e);
          }}
          className="space-y-6"
        >
          <FieldGroup>
            {/* Campo Email */}
            <Controller
              name="email"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel htmlFor="form-email">Email</FieldLabel>
                  <Input
                    {...field}
                    id="form-email"
                    type="email"
                    aria-invalid={fieldState.invalid}
                    required
                  />
                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            />
            <Controller
              name="senha"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <div className="flex items-center justify-between">
                    <FieldLabel htmlFor="form-password">Senha</FieldLabel>
                    <Link
                      to="/recoverPassword"
                      className="text-sm font-semibold text-indigo-400 hover:text-indigo-300"
                    >
                      Recuperar Senha
                    </Link>
                  </div>
                  <Input
                    {...field}
                    id="form-password"
                    type="password"
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
                Login
              </Button>
            </Field>
          </FieldGroup>
        </form>

        <p className="mt-10 text-center text-sm/6 text-gray-400">
          Não tem cadastro?
          <Link
            to="/register"
            class="font-semibold text-indigo-400 hover:text-indigo-300"
          >
            Cadastre-se
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
