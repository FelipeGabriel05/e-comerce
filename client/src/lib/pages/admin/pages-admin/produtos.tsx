import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { ConfirmDialog } from '@/components/ui/confirm-dialog';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { useAdminProducts } from '@/lib/hooks/use-admin-products';
import { useCategories } from '@/lib/hooks/use-categories';
import { ProductForm } from '@/lib/pages/admin/products/components/product-form';
import { StockBadge } from '@/lib/pages/admin/products/components/stock-badge';
import type { Product } from '@/lib/types/product';

import Layout from '../layout-sidebar';

const Produtos = () => {
  const {
    products,
    isLoading,
    createProduct,
    updateProduct,
    deleteProduct,
    isPending,
  } = useAdminProducts();

  const { categories } = useCategories();

  const [editandoProduct, setEditandoProduct] = useState<Product | null>(null);
  const [formOpen, setFormOpen] = useState(false);
  const [deletingProduct, setDeletingProduct] = useState<Product | null>(null);

  const closeForm = () => {
    setFormOpen(false);
    setEditandoProduct(null);
  };

  if (isLoading) return <p className="p-8 text-white">Carregando...</p>;

  return (
    <Layout>
      <div className="flex flex-col gap-6">
        <h1 className="text-2xl font-bold text-white">
          Gerenciamento de Produtos
        </h1>

        <Dialog
          open={formOpen}
          onOpenChange={(open) => {
            if (!open) closeForm();
          }}
        >
          <DialogContent className="sm:max-w-lg overflow-y-auto max-h-[90vh]">
            <DialogHeader>
              <DialogTitle>
                {editandoProduct ? 'Alterar Produto' : 'Inserir novo produto'}
              </DialogTitle>
            </DialogHeader>
            <ProductForm
              key={editandoProduct?.id ?? 'new'}
              isEditing={!!editandoProduct}
              isPending={isPending}
              categories={categories}
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
                } else {
                  createProduct(data);
                }
                closeForm();
              }}
              onCancel={closeForm}
            />
          </DialogContent>
        </Dialog>

        <div className="rounded-xl bg-white/5 border border-white/10 backdrop-blur-md overflow-hidden">
          <div className="flex justify-center p-4 border-b border-white/10">
            <Button
              className="bg-violet-600 hover:bg-violet-500 text-white px-8"
              onClick={() => {
                setEditandoProduct(null);
                setFormOpen(true);
              }}
            >
              + Inserir novo produto
            </Button>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Foto</TableHead>
                <TableHead>Descrição</TableHead>
                <TableHead>Preço</TableHead>
                <TableHead>Qtde (Estoque)</TableHead>
                <TableHead>Ações</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {products.map((product: Product) => (
                <TableRow key={product.id}>
                  <TableCell>{product.id}</TableCell>
                  <TableCell>
                    {product.foto ? (
                      <img
                        src={product.foto}
                        alt={product.descricao}
                        className="w-12 h-12 object-cover rounded-md"
                      />
                    ) : (
                      <div className="w-12 h-12 rounded-md bg-white/10 flex items-center justify-center text-white/20 text-xs">
                        Sem foto
                      </div>
                    )}
                  </TableCell>
                  <TableCell>{product.descricao}</TableCell>
                  <TableCell>
                    {new Intl.NumberFormat('pt-BR', {
                      style: 'currency',
                      currency: 'BRL',
                    }).format(product.preco)}
                  </TableCell>
                  <TableCell>
                    <StockBadge quantidade={product.quantidade} />
                  </TableCell>
                  <TableCell className="flex gap-2">
                    <Button
                      size="sm"
                      className="bg-violet-600 hover:bg-violet-500 text-white"
                      onClick={() => {
                        setEditandoProduct(product);
                        setFormOpen(true);
                      }}
                    >
                      Alterar
                    </Button>
                    <Button
                      size="sm"
                      variant="destructive"
                      onClick={() => setDeletingProduct(product)}
                    >
                      Excluir
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      </div>

      {deletingProduct && (
        <ConfirmDialog
          title="Confirmar exclusão"
          description={
            <>
              Deseja excluir{' '}
              <span className="text-white font-medium">
                "{deletingProduct.descricao}"
              </span>
              ? Esta ação não pode ser desfeita.
            </>
          }
          confirmLabel="Excluir"
          onConfirm={() => {
            deleteProduct(deletingProduct.id);
            setDeletingProduct(null);
          }}
          onCancel={() => setDeletingProduct(null)}
        />
      )}
    </Layout>
  );
};

export default Produtos;
