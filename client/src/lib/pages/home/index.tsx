import { useQuery } from '@tanstack/react-query';

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

  if (isLoading) return <p>Carregando produtos...</p>;

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Produtos</h1>
      <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
        {products.map((product) => (
          <div
            key={product.id}
            style={{
              border: '1px solid white',
              padding: '1rem',
              borderRadius: '8px',
            }}
          >
            <p>{product.descricao}</p>
            <p>R$ {product.preco}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;
