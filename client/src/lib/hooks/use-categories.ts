import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { toast } from 'sonner';

import type { Category } from '@/lib/services/categories.service';
import {
  deleteCategoryService,
  fetchCategories,
  insertCategoryService,
  updateCategoryService,
} from '@/lib/services/categories.service';

const CATEGORY_QUERY_KEY = ['categories'];

export const useCategories = () => {
  const queryClient = useQueryClient();

  const { data, isLoading } = useQuery<Array<Category>>({
    queryKey: CATEGORY_QUERY_KEY,
    queryFn: fetchCategories,
  });

  const createMutation = useMutation({
    mutationFn: insertCategoryService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: CATEGORY_QUERY_KEY });
      toast.success('Categoria criada com sucesso');
    },
    onError: () => toast.error('Erro ao criar categoria'),
  });

  const updateMutation = useMutation({
    mutationFn: updateCategoryService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: CATEGORY_QUERY_KEY });
      toast.success('Categoria atualizada com sucesso');
    },
    onError: () => toast.error('Erro ao atualizar categoria'),
  });

  const deleteMutation = useMutation({
    mutationFn: deleteCategoryService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: CATEGORY_QUERY_KEY });
      toast.success('Categoria excluída com sucesso');
    },
    onError: () => toast.error('Erro ao excluir categoria'),
  });

  return {
    categories: data ?? [],
    isLoading,
    createCategory: createMutation.mutate,
    updateCategory: updateMutation.mutate,
    deleteCategory: deleteMutation.mutate,
    isPending:
      createMutation.isPending ||
      updateMutation.isPending ||
      deleteMutation.isPending,
  };
};
