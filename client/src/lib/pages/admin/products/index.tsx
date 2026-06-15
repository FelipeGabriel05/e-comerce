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
import { useAdminProducts } from '@/lib/hooks/use-admin-products';
import type { Product } from '@/lib/types/product';

import { ProductForm } from './components/product-form';

const AdminProducts = () => {
  const {
    products,
    isLoading,
    createProduct,
    updateProduct,
    deleteProduct,
    isPending,
  } = useAdminProducts();
  const [editandoProduct, setEditandoProduct] = useState<Product | null>(null);

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
            isPending={isPending}
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
            onSubmit={(data) => {
              if (editandoProduct) {
                updateProduct({ id: editandoProduct.id, data });
                setEditandoProduct(null);
              } else {
                createProduct(data);
              }
            }}
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
                      onClick={() => deleteProduct(product.id)}
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
