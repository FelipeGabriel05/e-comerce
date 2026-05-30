import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from '@tanstack/react-router';
import { toast } from 'sonner';

import type { LoginFormData } from '@/lib/pages/login/schema';
import { api } from '@/lib/services/constants';

/* aq é o tipo do user retornado pela API  */
export type AuthUser = {
  id: number;
  login: string;
  nome: string;
  email: string;
  endereco: string;
  administrador: boolean;
};

const ME_KEY = ['auth', 'me'];

/* aq o hook  */
export function useAuth() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  /* Busca o usuário logado. Se a sessão expirou, a API retorna erro */
  const { data: user = null, isLoading } = useQuery<AuthUser | null>({
    queryKey: ME_KEY,
    queryFn: async () => {
      try {
        const response = await api.get('/me');
        return response.data.data as AuthUser;
      } catch {
        // Sessão inexistente ou expirada ent o user n foi autenticado
        return null;
      }
    },
    retry: false,
    staleTime: 1000 * 60 * 5, // revalida a cada 5 minutos
  });

  /* ── login ── */
  const loginMutation = useMutation({
    mutationFn: (data: LoginFormData) => api.post('/login', data),
    onSuccess: async (response) => {
      const loggedUser: AuthUser = response.data.data;
      // aq preenche o cache do /me sem fazer nova requisição
      queryClient.setQueryData(ME_KEY, loggedUser);
      toast('Login realizado!');
      navigate({ to: loggedUser.administrador ? '/admin' : '/cliente' });
    },
    onError: () => {
      toast.error('Login falhou. Verifique suas credenciais.');
    },
  });

  /* logout */
  const logoutMutation = useMutation({
    mutationFn: () => api.post('/logout'),
    onSettled: () => {
      // ele limpa o cache independente de erro (sessão pode já ter expirado)
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

    /** Força revalidação do /me (útil após alterar dados do usuário). */
    refreshUser: () => queryClient.invalidateQueries({ queryKey: ME_KEY }),
  };
}
