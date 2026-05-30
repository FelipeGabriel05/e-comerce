import { useQuery } from '@tanstack/react-query';

import { H1 } from '@/components/ui/typography';
import { ProductCard } from '@/lib/components/product-card';
import { fetchProducts } from '@/lib/services/products';
import type { Product } from '@/lib/types/product';

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

const Home = () => {
  const { data, isLoading } = useQuery({
    queryKey: ['products'],
    queryFn: fetchProducts,
  });

  const products = data ?? mockProducts;

  if (isLoading)
    return <p className="text-white p-8">Carregando produtos...</p>;

  return (
    <div className="p-8">
      <H1 className="mb-6 text-white">Produtos</H1>
      <div className="flex flex-wrap gap-4">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default Home;
