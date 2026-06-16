import type { ProductFormData } from '@/lib/pages/admin/products/product-validation-schema';
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

export const createProductService = async (
  data: ProductFormData,
): Promise<void> => {
  await api.post('/admin/products', data);
};

export const updateProductService = async ({
  id,
  data,
}: {
  id: number;
  data: ProductFormData;
}): Promise<void> => {
  await api.put(`/admin/products/${id}`, data);
};

export const deleteProductService = async (id: number): Promise<void> => {
  await api.delete(`/admin/products/${id}`);
};
