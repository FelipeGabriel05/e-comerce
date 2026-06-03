import { useQuery } from '@tanstack/react-query';

import { fetchProducts } from '@/lib/services/products';
import type { Product } from '@/lib/types/product';

const PRODUCTS_QUERY_KEY = ['products'];

export const useProducts = () => {
  const { data, isLoading } = useQuery<Array<Product>>({
    queryKey: PRODUCTS_QUERY_KEY,
    queryFn: fetchProducts,
  });

  return {
    products: data ?? [],
    isLoading,
  };
};
