import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { useProducts } from '@/lib/hooks/use-products';
import { api } from '@/lib/services/constants';
import type { Product } from '@/lib/types/product';

const PRODUCTS_QUERY_KEY = ['products'];

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
      await api.delete(`/admin/products/${id}`);
    },
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  const createMutation = useMutation({
    mutationFn: async () => {
      await api.post('/admin/products', {
        ...novoProduct,
        preco: Number(novoProduct.preco),
        quantidade: Number(novoProduct.quantidade),
      });
    },
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  const updateMutation = useMutation({
    mutationFn: async () => {
      await api.put(`/admin/products/${editandoId}`, {
        ...novoProduct,
        preco: Number(novoProduct.preco),
        quantidade: Number(novoProduct.quantidade),
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY });
      resetForm();
    },
  });

  const resetForm = () => {
    setEditandoId(null);
    setNovoProduct({
      descricao: '',
      preco: '',
      foto: '',
      quantidade: '',
      categoriaId: 1,
    });
  };

  if (isLoading) return <p className="p-8 text-white">Carregando...</p>;

  return (
    <div className="p-8 flex flex-col gap-8">
      <h1 className="text-2xl font-bold text-white">Gerenciar Produtos</h1>

      <Card>
        <CardHeader>
          <CardTitle>
            {editandoId ? 'Editar Produto' : 'Novo Produto'}
          </CardTitle>
        </CardHeader>
        <CardContent className="flex flex-col gap-3">
          <Input
            placeholder="Descrição"
            value={novoProduct.descricao}
            onChange={(e) =>
              setNovoProduct({ ...novoProduct, descricao: e.target.value })
            }
          />
          <Input
            type="number"
            placeholder="Preço"
            value={novoProduct.preco}
            onChange={(e) =>
              setNovoProduct({ ...novoProduct, preco: e.target.value })
            }
          />
          <Input
            type="number"
            placeholder="Quantidade"
            value={novoProduct.quantidade}
            onChange={(e) =>
              setNovoProduct({ ...novoProduct, quantidade: e.target.value })
            }
          />
          <div className="flex gap-2">
            <Button
              onClick={() =>
                editandoId ? updateMutation.mutate() : createMutation.mutate()
              }
              className="bg-violet-600 hover:bg-violet-500 text-white"
            >
              {editandoId ? 'Salvar Alterações' : 'Criar Produto'}
            </Button>
            {editandoId && (
              <Button variant="outline" onClick={resetForm}>
                Cancelar
              </Button>
            )}
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Descrição</TableHead>
                <TableHead>Preço</TableHead>
                <TableHead>Qtd</TableHead>
                <TableHead>Ações</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {products.map((product: Product) => (
                <TableRow key={product.id}>
                  <TableCell>#{product.id}</TableCell>
                  <TableCell>{product.descricao}</TableCell>
                  <TableCell>
                    R$
                    {new Intl.NumberFormat('pt-BR', {
                      style: 'currency',
                      currency: 'BRL',
                    }).format(product.preco)}
                  </TableCell>
                  <TableCell>{product.quantidade}</TableCell>
                  <TableCell className="flex gap-2">
                    <Button
                      size="sm"
                      variant="outline"
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
                    </Button>
                    <Button
                      size="sm"
                      variant="destructive"
                      onClick={() => deleteMutation.mutate(product.id)}
                    >
                      Deletar
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
};

export default AdminProducts;
