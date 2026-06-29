import { useMutation, useQueryClient } from '@tanstack/react-query';
import { toast } from 'sonner';

import { checkoutService } from '@/lib/services/checkout.services';

const CART_QUERY_KEY = ['cart'];

export const useFinalizeCart = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: checkoutService,
    onSuccess: () => {
      toast.success('Compra finalizada com sucesso!');
      queryClient.invalidateQueries({ queryKey: CART_QUERY_KEY });
    },
    onError: () => {
      toast.error('Erro ao finalizar compra. Tente novamente.');
    },
  });

  return {
    finalizeCart: mutation.mutate,
    isSubmitting: mutation.isPending,
  };
};
