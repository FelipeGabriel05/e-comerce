import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { toast } from 'sonner';

import type { Sale } from '../services/sales.service';
import {
  deleteSaleService,
  fetchSalesService,
} from '../services/sales.service';

const SALES_QUERY_KEY = ['sales'];

export const useSales = () => {
  const queryClient = useQueryClient();
  const { data, isLoading } = useQuery<Array<Sale>>({
    queryKey: SALES_QUERY_KEY,
    queryFn: fetchSalesService,
  });

  const deleteMutation = useMutation({
    mutationFn: deleteSaleService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: SALES_QUERY_KEY });
      toast.success('Venda excluída com sucesso');
    },
    onError: () => toast.error('Erro ao excluir venda'),
  });

  return {
    sales: data ?? [],
    isLoading,
    deleteSale: deleteMutation.mutate,
    isPending: deleteMutation.isPending,
  };
};
