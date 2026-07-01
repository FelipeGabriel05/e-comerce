import { useQuery } from '@tanstack/react-query';

import type { Compras } from '../services/compras.services';
import { fetchCompras } from '../services/compras.services';

const SHOPPING_QUERY_KEY = ['shopping'];

export const useShopping = () => {
  const { data, isLoading } = useQuery<Array<Compras>>({
    queryKey: SHOPPING_QUERY_KEY,
    queryFn: fetchCompras,
  });

  return {
    shopping: data ?? [],
    isLoading,
  };
};
