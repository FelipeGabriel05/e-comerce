import { zodResolver } from '@hookform/resolvers/zod';
import { Link } from '@tanstack/react-router';
import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';

import { Button } from '@/components/ui/button';
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { useUserProfile } from '@/lib/hooks/use-user-profile';

import type { RegisterFormDataEdit } from './schema-dados-pessoais';
import { RegisterEditionSchema } from './schema-dados-pessoais';

const DadosPessoaisPage = () => {
  const {
    user,
    // isLoadingUser,
    updateUser,
    deleteUser,
    isUpdating,
  } = useUserProfile();

  const form = useForm<RegisterFormDataEdit>({
    resolver: zodResolver(RegisterEditionSchema),
    mode: 'onChange',
    defaultValues: {
      name: '',
      address: '',
      email: '',
      login: '',
      password: '',
    },
  });

  function onSubmit(data: RegisterFormDataEdit) {
    if (!user) return;

    updateUser({
      id: user.id,
      data: data,
    });
  }

  function handleDeleteAccount() {
    if (!user) return;

    const confirmed = window.confirm(
      'Tem certeza que deseja excluir sua conta? Esta ação não pode ser desfeita.',
    );

    if (!confirmed) return;

    deleteUser(user.id);
  }

  useEffect(() => {
    if (!user) return;

    form.reset({
      name: user.nome,
      address: user.endereco,
      email: user.email,
      login: user.login,
      password: '',
    });
  }, [user, form]);

  return (
    <div className="mx-auto w-full max-w-3xl rounded-2xl bg-formblack p-8 text-white shadow-lg">
      <div className="mb-8">
        <h1 className="text-3xl font-bold">Editar Perfil</h1>

        <p className="mt-2 text-sm text-zinc-400">
          Atualize suas informações pessoais e credenciais de acesso.
        </p>
      </div>

      <form
        onSubmit={(e) => {
          e.preventDefault();
          form.handleSubmit(onSubmit)(e);
        }}
        className="space-y-8"
      >
        <div>
          <h2 className="mb-4 text-lg font-semibold">Informações Pessoais</h2>

          <FieldGroup className="grid gap-4 md:grid-cols-2">
            <Controller
              name="name"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel htmlFor="form-name">Nome</FieldLabel>

                  <Input
                    {...field}
                    id="form-name"
                    placeholder="Nome completo"
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            />

            <Controller
              name="email"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel htmlFor="form-email">E-mail</FieldLabel>

                  <Input
                    {...field}
                    id="form-email"
                    type="email"
                    placeholder="email@exemplo.com"
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            />

            <Controller
              name="address"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field
                  className="md:col-span-2"
                  data-invalid={fieldState.invalid}
                >
                  <FieldLabel htmlFor="form-address">Endereço</FieldLabel>

                  <Input
                    {...field}
                    id="form-address"
                    placeholder="Rua, bairro, número..."
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            />
          </FieldGroup>
        </div>

        <div className="border-t border-zinc-700 pt-6">
          <h2 className="mb-4 text-lg font-semibold">Dados de Acesso</h2>

          <FieldGroup className="grid gap-4 md:grid-cols-2">
            <Controller
              name="login"
              control={form.control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel htmlFor="form-login">Nome de usuário</FieldLabel>

                  <Input {...field} id="form-login" placeholder="Seu login" />

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
                  <FieldLabel htmlFor="form-password">Nova senha</FieldLabel>

                  <Input
                    {...field}
                    id="form-password"
                    type="password"
                    placeholder="Preencha apenas se desejar alterar"
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            />
          </FieldGroup>
        </div>

        <div className="flex flex-wrap justify-end gap-3 border-t border-zinc-700 pt-6">
          <Button
            type="button"
            variant="outline"
            className="border-zinc-700 bg-zinc-800 text-white hover:bg-zinc-700"
            render={<Link to="/cliente">Cancelar</Link>}
          />

          <Button type="submit" disabled={isUpdating}>
            {isUpdating ? 'Salvando...' : 'Salvar Alterações'}
          </Button>
        </div>

        <div className="border-t border-red-900 pt-6">
          <p className="mt-2 text-sm text-zinc-400">
            A exclusão da conta é permanente e remove todos os dados associados
            ao usuário.
          </p>

          <Button
            type="button"
            variant="destructive"
            className="mt-4"
            onClick={handleDeleteAccount}
          >
            Excluir Conta
          </Button>
        </div>
      </form>
    </div>
  );
};

export default DadosPessoaisPage;
