import type { Product } from '@/lib/types/product';

export const fetchProducts = async (): Promise<Array<Product>> => {
  const response = await fetch('http://localhost:8080/products');

  if (!response.ok) {
    throw new Error('Erro ao buscar produtos');
  }

  const data = await response.json();
  return data;
};