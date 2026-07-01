import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { toast } from 'sonner';

import type { Sale } from '../services/sales.service';
import {
  deleteSaleService,
  fetchSale,
  fetchSalesService,
} from '../services/sales.service';

const SALES_QUERY_KEY = ['sales'];

export const useSalesAdm = () => {
  const queryClient = useQueryClient();
  const { data, isLoading } = useQuery<Array<Sale>>({
    queryKey: SALES_QUERY_KEY,
    queryFn: fetchSale,
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

export const useSales = () => {
  const { data: sales, isLoading } = useQuery<Array<Sale>>({
    queryKey: SALES_QUERY_KEY,
    queryFn: async () => fetchSalesService(),
  });

  return {
    sales: sales ?? [],
    isLoading,
  };
};
