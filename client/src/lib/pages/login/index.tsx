import { zodResolver } from '@hookform/resolvers/zod';
import { useMutation } from '@tanstack/react-query';
import { Link, useNavigate } from '@tanstack/react-router';
import { Controller, useForm } from 'react-hook-form';
import { toast } from 'sonner';

import { Button } from '@/components/ui/button';
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { loginService } from '@/lib/services/auth.services';

import type { LoginFormData } from './schema';
import { LoginValidationSchema } from './schema';

const Login = () => {
  const form = useForm({
    resolver: zodResolver(LoginValidationSchema),
    mode: 'onChange',
    defaultValues: {
      login: '',
      password: '',
    },
  });

  const nav = useNavigate();

  const mut = useMutation({
    mutationFn: loginService,

    onSuccess: (res) => {
      if (res.data.administrador) {
        nav({ to: '/admin' });
      } else {
        nav({ to: '/cliente' });
      }
      toast('Login realizado!');
    },

    onError: (err) => {
      console.error(err);
      toast('Login falhou');
    },
  });

  function onSubmit(d: LoginFormData) {
    mut.mutate(d);
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
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
          <FieldGroup>
            <Controller
              name="login"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel htmlFor="form-Username">
                    Nome de usuário
                  </FieldLabel>
                  <Input
                    {...field}
                    id="form-Username"
                    type="text"
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
              name="password"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <div className="flex items-center justify-between">
                    <FieldLabel htmlFor="form-password">Senha</FieldLabel>
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
              <Link to="/" className="block w-full text-center">
                <Button
                  type="button"
                  className="mt-4 w-48 rounded-md bg-indigo-500 font-semibold hover:bg-indigo-400"
                >
                  Voltar
                </Button>
              </Link>
              <Button
                disabled={mut.isPending}
                type="submit"
                className="mt-4 w-48 rounded-md bg-indigo-500 py-2 font-semibold hover:bg-indigo-400"
              >
                {mut.isPending ? 'Entrando...' : 'Entrar'}
              </Button>
            </Field>
          </FieldGroup>
        </form>

        <p className="mt-10 text-center text-sm/6 text-gray-400">
          Não tem cadastro?
          <Link
            to="/cadastrar"
            className="font-semibold text-indigo-400 hover:text-indigo-300"
          >
            Cadastre-se
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
