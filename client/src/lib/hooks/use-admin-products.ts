import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import type { ProductFormData } from '@/lib/pages/admin/products/product-validation-schema';
import { api } from '@/lib/services/constants';
import { fetchProducts } from '@/lib/services/products';
import type { Product } from '@/lib/types/product';

const PRODUCTS_QUERY_KEY = ['products'];

export const useAdminProducts = () => {
  const queryClient = useQueryClient();

  const { data, isLoading } = useQuery<Array<Product>>({
    queryKey: PRODUCTS_QUERY_KEY,
    queryFn: fetchProducts,
  });

  const createMutation = useMutation({
    mutationFn: async (data: ProductFormData) => {
      await api.post('/admin/products', data);
    },
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  const updateMutation = useMutation({
    mutationFn: async ({ id, data }: { id: number; data: ProductFormData }) => {
      await api.put(`/admin/products/${id}`, data);
    },
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  const deleteMutation = useMutation({
    mutationFn: async (id: number) => {
      await api.delete(`/admin/products/${id}`);
    },
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  return {
    products: data ?? [],
    isLoading,
    createProduct: createMutation.mutate,
    updateProduct: updateMutation.mutate,
    deleteProduct: deleteMutation.mutate,
    isPending: createMutation.isPending || updateMutation.isPending,
  };
};
