import { useQuery } from '@tanstack/react-query';

import type { Valor } from '../services/report-daily.services';
import { fetchDaily } from '../services/report-daily.services';

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
