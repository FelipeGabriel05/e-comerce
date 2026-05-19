import type { Product } from '@/lib/types/product';

export const mockProducts: Array<Product> = [
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
