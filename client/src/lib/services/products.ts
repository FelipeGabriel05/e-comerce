import type { Product } from '@/lib/types/product';

import { api } from './constants';

export const fetchProducts = async (): Promise<Array<Product>> => {
  const response = await api.get<Array<Product>>('/products');
  return response.data;
};
