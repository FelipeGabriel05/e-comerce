import { useMutation, useQuery } from '@tanstack/react-query';
import { toast } from 'sonner';

import {
  deleteUserService,
  getMeService,
  updateUserService,
} from '@/lib/services/auth.services';

import type { RegisterFormDataEdit } from '../pages/clients/schema-dados-pessoais';

export function useUserProfile() {
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

  return {
    user: userQuery.data,
    isLoadingUser: userQuery.isLoading,
    updateUser: updateUserMutation.mutate,
    deleteUser: deleteUserMutation.mutate,
    isDeleting: deleteUserMutation.isPending,
    isUpdating: updateUserMutation.isPending,
  };
}
