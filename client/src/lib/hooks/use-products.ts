import { useQuery } from '@tanstack/react-query';

import type { Product } from '@/lib/types/product';

const PRODUCTS_QUERY_KEY = ['products'];

const mockProducts: Array<Product> = [
  {
    id: 1,
    descricao: 'God of War',
    preco: 199.9,
    foto: '',
    quantidade: 10,
    categoriaId: 1,
  },
  {
    id: 2,
    descricao: 'Spider-Man 2',
    preco: 249.9,
    foto: '',
    quantidade: 5,
    categoriaId: 1,
  },
  {
    id: 3,
    descricao: 'Hogwarts Legacy',
    preco: 179.9,
    foto: '',
    quantidade: 0,
    categoriaId: 2,
  },
];

export const useProducts = () => {
  const { data, isLoading } = useQuery<Array<Product>>({
    queryKey: PRODUCTS_QUERY_KEY,
    queryFn: async () => mockProducts,
  });

  return {
    products: data ?? mockProducts,
    isLoading,
  };
};
