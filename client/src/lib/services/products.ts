import type { Product } from '@/lib/types/product';

import { api } from './constants';

interface ProductsResponse {
  status: number;
  message: string;
  data: Array<Product>;
}

export const fetchProducts = async (): Promise<Array<Product>> => {
  const response = await api.get<ProductsResponse>('/products');
  return response.data.data;
};
