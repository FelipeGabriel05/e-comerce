import { H1, P } from '@/components/ui/typography';
import { ProductCard } from '@/lib/components/product-card';
import { useProducts } from '@/lib/hooks/use-products';

const Home = () => {
  const { products, isLoading } = useProducts();

  if (isLoading)
    return <P className="text-white p-8">Carregando produtos...</P>;

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
