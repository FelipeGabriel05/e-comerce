import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from '@tanstack/react-router';
import { toast } from 'sonner';

import {
  deleteUserService,
  getMeService,
  logoutService,
  updateUserService,
} from '@/lib/services/auth.services';

import type { RegisterFormDataEdit } from '../pages/clients/schema-dados-pessoais';

export function useUserProfile() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const ME_KEY = ['auth', 'me'];

  const userQuery = useQuery({
    queryKey: ['me'],
    queryFn: getMeService,
  });

  const updateUserMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: RegisterFormDataEdit }) =>
      updateUserService(id, data),

    onSuccess: () => {
      toast.success('Dados atualizados com sucesso!');
    },

    onError: () => {
      toast.error('Falha ao atualizar os dados.');
    },
  });

  const deleteUserMutation = useMutation({
    mutationFn: (id: number) => deleteUserService(id),

    onSuccess: () => {
      toast.success('Conta excluída com sucesso!');
    },

    onError: () => {
      toast.error('Falha ao excluir conta.');
    },
  });

  const logoutMutation = useMutation({
    mutationFn: logoutService,
    onSettled: () => {
      queryClient.setQueryData(ME_KEY, null);
      queryClient.clear();
      navigate({ to: '/' });
      toast('Sessão encerrada.');
    },
  });

  return {
    user: userQuery.data,
    isLoadingUser: userQuery.isLoading,
    updateUser: updateUserMutation.mutate,
    deleteUser: deleteUserMutation.mutate,
    isDeleting: deleteUserMutation.isPending,
    isUpdating: updateUserMutation.isPending,
    logout: () => logoutMutation.mutate(),
    isLogoutPending: logoutMutation.isPending,
  };
}
