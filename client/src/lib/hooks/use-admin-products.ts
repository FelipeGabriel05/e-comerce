import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { toast } from 'sonner';

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
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY });
      toast.success('Produto criado com sucesso');
    },
    onError: () => toast.error('Erro ao criar produto'),
  });

  const updateMutation = useMutation({
    mutationFn: updateProductService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY });
      toast.success('Produto atualizado com sucesso');
    },
    onError: () => toast.error('Erro ao atualizar produto'),
  });

  const deleteMutation = useMutation({
    mutationFn: deleteProductService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY });
      toast.success('Produto excluído com sucesso');
    },
    onError: () => toast.error('Erro ao excluir produto'),
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
