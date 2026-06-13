import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';

import { useProducts } from '@/lib/hooks/use-products';
import type { Product } from '@/lib/types/product';

const AdminProducts = () => {
  const queryClient = useQueryClient();
  const { products, isLoading } = useProducts();

  const [novoProduct, setNovoProduct] = useState({
    descricao: '',
    preco: '',
    foto: '',
    quantidade: '',
    categoriaId: 1,
  });

  const [editandoId, setEditandoId] = useState<number | null>(null);

  const deleteMutation = useMutation({
    mutationFn: async (id: number) => {
      const response = await fetch(
        `http://localhost:8080/admin/products/${id}`,
        { method: 'DELETE' },
      );
      if (!response.ok) throw new Error('Erro ao deletar');
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] });
    },
  });

  const createMutation = useMutation({
    mutationFn: async () => {
      const response = await fetch('http://localhost:8080/admin/products', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          ...novoProduct,
          preco: Number(novoProduct.preco),
          quantidade: Number(novoProduct.quantidade),
        }),
      });
      if (!response.ok) throw new Error('Erro ao criar produto');
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] });
    },
  });

  const updateMutation = useMutation({
    mutationFn: async () => {
      const response = await fetch(
        `http://localhost:8080/admin/products/${editandoId}`,
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            ...novoProduct,
            preco: Number(novoProduct.preco),
            quantidade: Number(novoProduct.quantidade),
          }),
        },
      );
      if (!response.ok) throw new Error('Erro ao editar produto');
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] });
      setEditandoId(null);
      setNovoProduct({
        descricao: '',
        preco: '',
        foto: '',
        quantidade: '',
        categoriaId: 1,
      });
    },
  });

  if (isLoading) return <p>Carregando...</p>;

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Gerenciar Produtos</h1>

      <div
        style={{
          marginTop: '1rem',
          marginBottom: '2rem',
          display: 'flex',
          flexDirection: 'column',
          gap: '0.5rem',
          maxWidth: '400px',
        }}
      >
        <h2>{editandoId ? 'Editar Produto' : 'Novo Produto'}</h2>
        <input
          placeholder="Descrição"
          value={novoProduct.descricao}
          onChange={(e) =>
            setNovoProduct({ ...novoProduct, descricao: e.target.value })
          }
          style={{ padding: '0.5rem', borderRadius: '6px' }}
        />
        <input
          type="number"
          placeholder="Preço"
          value={novoProduct.preco}
          onChange={(e) =>
            setNovoProduct({ ...novoProduct, preco: e.target.value })
          }
          style={{ padding: '0.5rem', borderRadius: '6px' }}
        />
        <input
          type="number"
          placeholder="Quantidade"
          value={novoProduct.quantidade}
          onChange={(e) =>
            setNovoProduct({ ...novoProduct, quantidade: e.target.value })
          }
          style={{ padding: '0.5rem', borderRadius: '6px' }}
        />
        <button
          type="button"
          onClick={() =>
            editandoId ? updateMutation.mutate() : createMutation.mutate()
          }
          style={{
            padding: '0.5rem',
            backgroundColor: '#7c3aed',
            color: 'white',
            borderRadius: '6px',
            cursor: 'pointer',
          }}
        >
          {editandoId ? 'Salvar Alterações' : 'Criar Produto'}
        </button>

        {editandoId && (
          <button
            type="button"
            onClick={() => {
              setEditandoId(null);
              setNovoProduct({
                descricao: '',
                preco: '',
                foto: '',
                quantidade: '',
                categoriaId: 1,
              });
            }}
            style={{
              padding: '0.5rem',
              backgroundColor: 'transparent',
              color: 'white',
              borderRadius: '6px',
              cursor: 'pointer',
              border: '1px solid white',
            }}
          >
            Cancelar
          </button>
        )}
      </div>

      <table
        style={{ width: '100%', borderCollapse: 'collapse', marginTop: '1rem' }}
      >
        <thead>
          <tr>
            <th
              style={{
                textAlign: 'left',
                padding: '0.5rem',
                borderBottom: '1px solid white',
              }}
            >
              ID
            </th>
            <th
              style={{
                textAlign: 'left',
                padding: '0.5rem',
                borderBottom: '1px solid white',
              }}
            >
              Descrição
            </th>
            <th
              style={{
                textAlign: 'left',
                padding: '0.5rem',
                borderBottom: '1px solid white',
              }}
            >
              Preço
            </th>
            <th
              style={{
                textAlign: 'left',
                padding: '0.5rem',
                borderBottom: '1px solid white',
              }}
            >
              Qtd
            </th>
            <th
              style={{
                textAlign: 'left',
                padding: '0.5rem',
                borderBottom: '1px solid white',
              }}
            >
              Ações
            </th>
          </tr>
        </thead>
        <tbody>
          {products.map((product: Product) => (
            <tr key={product.id}>
              <td style={{ padding: '0.5rem' }}>#{product.id}</td>
              <td style={{ padding: '0.5rem' }}>{product.descricao}</td>
              <td style={{ padding: '0.5rem' }}>R$ {product.preco}</td>
              <td style={{ padding: '0.5rem' }}>{product.quantidade}</td>
              <td style={{ padding: '0.5rem', display: 'flex', gap: '0.5rem' }}>
                <button
                  type="button"
                  onClick={() => {
                    setEditandoId(product.id);
                    setNovoProduct({
                      descricao: product.descricao,
                      preco: String(product.preco),
                      foto: product.foto,
                      quantidade: String(product.quantidade),
                      categoriaId: product.categoriaId,
                    });
                  }}
                >
                  Editar
                </button>
                <button
                  type="button"
                  onClick={() => deleteMutation.mutate(product.id)}
                >
                  Deletar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminProducts;
