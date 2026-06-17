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
  const formData = new FormData();
  formData.append('descricao', data.descricao);
  formData.append('preco', String(data.preco));
  formData.append('quantidade', String(data.quantidade));
  formData.append('categoriaId', String(data.categoriaId));
  if (data.foto instanceof File) {
    formData.append('foto', data.foto);
  }

  await api.post('/admin/products', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const updateProductService = async ({
  id,
  data,
}: {
  id: number;
  data: ProductFormData;
}): Promise<void> => {
  const formData = new FormData();
  formData.append('descricao', data.descricao);
  formData.append('preco', String(data.preco));
  formData.append('quantidade', String(data.quantidade));
  formData.append('categoriaId', String(data.categoriaId));
  if (data.foto instanceof File) {
    formData.append('foto', data.foto);
  }

  await api.put(`/admin/products/${id}`, formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const deleteProductService = async (id: number): Promise<void> => {
  await api.delete(`/admin/products/${id}`);
};
