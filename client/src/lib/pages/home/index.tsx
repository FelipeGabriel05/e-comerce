import { useQuery } from '@tanstack/react-query';

import { H1 } from '@/components/ui/typography';
import { ProductCard } from '@/lib/components/product-card';
import { fetchProducts } from '@/lib/services/products';

import { mockProducts } from './mock';

const Home = () => {
  const { data, isLoading } = useQuery({
    queryKey: ['products'],
    queryFn: fetchProducts,
  });

  const products = data ?? mockProducts;

  if (isLoading) return <p>Carregando produtos...</p>;

  return (
    <div className="p-8">
      <H1 className="mb-6">Produtos</H1>
      <div className="flex flex-wrap gap-4">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default Home;
