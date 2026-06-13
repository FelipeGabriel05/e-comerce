import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { useProducts } from '@/lib/hooks/use-products';
import type { ProductFormData } from '@/lib/pages/admin/products/schema';
import { api } from '@/lib/services/constants';
import type { Product } from '@/lib/types/product';

import { ProductForm } from './components/product-form';

const PRODUCTS_QUERY_KEY = ['products'];

const AdminProducts = () => {
  const queryClient = useQueryClient();
  const { products, isLoading } = useProducts();
  const [editandoProduct, setEditandoProduct] = useState<Product | null>(null);

  const createMutation = useMutation({
    mutationFn: async (data: ProductFormData) => {
      await api.post('/admin/products', data);
    },
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  const updateMutation = useMutation({
    mutationFn: async (data: ProductFormData) => {
      await api.put(`/admin/products/${editandoProduct?.id}`, data);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY });
      setEditandoProduct(null);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: async (id: number) => {
      await api.delete(`/admin/products/${id}`);
    },
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: PRODUCTS_QUERY_KEY }),
  });

  if (isLoading) return <p className="p-8 text-white">Carregando...</p>;

  return (
    <div className="p-8 flex flex-col gap-8">
      <h1 className="text-2xl font-bold text-white">Gerenciar Produtos</h1>

      <Card>
        <CardHeader>
          <CardTitle>
            {editandoProduct ? 'Editar Produto' : 'Novo Produto'}
          </CardTitle>
        </CardHeader>
        <CardContent>
          <ProductForm
            isEditing={!!editandoProduct}
            isPending={createMutation.isPending || updateMutation.isPending}
            defaultValues={
              editandoProduct
                ? {
                    descricao: editandoProduct.descricao,
                    preco: editandoProduct.preco,
                    foto: editandoProduct.foto,
                    quantidade: editandoProduct.quantidade,
                    categoriaId: editandoProduct.categoriaId,
                  }
                : undefined
            }
            onSubmit={(data) =>
              editandoProduct
                ? updateMutation.mutate(data)
                : createMutation.mutate(data)
            }
            onCancel={() => setEditandoProduct(null)}
          />
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
                      onClick={() => setEditandoProduct(product)}
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
