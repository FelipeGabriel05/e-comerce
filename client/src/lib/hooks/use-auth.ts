import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from '@tanstack/react-router';
import { toast } from 'sonner';

import type { LoginFormData } from '@/lib/pages/login/schema';

import {
  getMeService,
  loginService,
  logoutService,
} from '../services/auth.services';

export type AuthUser = {
  id: number;
  login: string;
  nome: string;
  email: string;
  endereco: string;
  administrador: boolean;
};

const ME_KEY = ['auth', 'me'];
const FIVE_MINUTES_STALE_TIME = 1000 * 60 * 5;

export function useAuth() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { data: user = null, isLoading } = useQuery<AuthUser | null>({
    queryKey: ME_KEY,
    queryFn: getMeService,
    retry: false,
    staleTime: FIVE_MINUTES_STALE_TIME,
  });

  const loginMutation = useMutation({
    mutationFn: loginService,
    onSuccess: async (response) => {
      const loggedUser: AuthUser = response.data;
      queryClient.setQueryData(ME_KEY, loggedUser);
      toast('Login realizado!');
      navigate({ to: loggedUser.administrador ? '/admin' : '/cliente' });
    },
    onError: () => {
      toast.error('Login falhou. Verifique suas credenciais.');
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
    user,
    isAuthenticated: user !== null,
    isAdmin: user?.administrador === true,
    isLoading,

    login: (data: LoginFormData) => loginMutation.mutate(data),
    isLoginPending: loginMutation.isPending,

    logout: () => logoutMutation.mutate(),
    isLogoutPending: logoutMutation.isPending,

    refreshUser: () => queryClient.invalidateQueries({ queryKey: ME_KEY }),
  };
}
