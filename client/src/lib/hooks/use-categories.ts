import { useQuery } from '@tanstack/react-query';

import type { Category } from '@/lib/services/categories.service';
import { fetchCategories } from '@/lib/services/categories.service';

export const useCategories = () => {
  const { data, isLoading } = useQuery<Array<Category>>({
    queryKey: ['categories'],
    queryFn: fetchCategories,
  });

  return {
    categories: data ?? [],
    isLoading,
  };
};
