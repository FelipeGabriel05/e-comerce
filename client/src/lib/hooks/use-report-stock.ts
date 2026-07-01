import { useQuery } from '@tanstack/react-query';

import type { faltaEstoque } from '../services/report-stock.service';
import { fetchStock } from '../services/report-stock.service';

const STOCK_QUERY_KEY = ['stock'];

export const useStock = () => {
  const { data, isLoading } = useQuery<Array<faltaEstoque>>({
    queryKey: STOCK_QUERY_KEY,
    queryFn: fetchStock,
  });

  return {
    stock: data ?? [],
    isLoading,
  };
};
