import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import {
  createProductService,
  deleteProductService,
  fetchProducts,
  updateProductService,
} from '@/lib/services/products';
import type { Product } from '@/lib/types/product';

const PRODUCTS_QUERY_KEY = ['products'];

export const useAdminProducts = () => {
  const queryClient = useQueryClient();

  const { data, isLoading } = useQuery<Array<Product>>({
    queryKey: PRODUCTS_QUERY_KEY,
    queryFn: fetchProducts,
  });

  const createMutation = useMutation({
    mutationFn: createProductService,
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  const updateMutation = useMutation({
    mutationFn: updateProductService,
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  const deleteMutation = useMutation({
    mutationFn: deleteProductService,
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  return {
    products: data ?? [],
    isLoading,
    createProduct: createMutation.mutate,
    updateProduct: updateMutation.mutate,
    deleteProduct: deleteMutation.mutate,
    isPending:
      createMutation.isPending ||
      updateMutation.isPending ||
      deleteMutation.isPending,
  };
};
