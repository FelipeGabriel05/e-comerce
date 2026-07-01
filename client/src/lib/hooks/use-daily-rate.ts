import { useQuery } from '@tanstack/react-query';

import type { Valor } from '../services/valor-dia.services';
import { fetchDaily } from '../services/valor-dia.services';

const DAILY_QUERY_KEY = ['stock'];

export const useDaily = () => {
  const { data, isLoading } = useQuery<Array<Valor>>({
    queryKey: DAILY_QUERY_KEY,
    queryFn: fetchDaily,
  });

  return {
    daily: data ?? [],
    isLoading,
  };
};
