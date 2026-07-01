import { useQuery } from '@tanstack/react-query';

import { fetchSalesService, type Sale } from '@/lib/services/sales.service';

const SALES_QUERY_KEY = ['sales'];

export const UseSales = ['sales'];

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
