import { Pencil, Trash2 } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { TableCell, TableRow } from '@/components/ui/table';
import { useAdminProducts } from '@/lib/hooks/use-admin-products';
import { useCategories } from '@/lib/hooks/use-categories';
import { AdminCrudPage } from '@/lib/pages/admin/components/admin-crud-page';
import { ProductForm } from '@/lib/pages/admin/products/components/product-form';
import { StockBadge } from '@/lib/pages/admin/products/components/stock-badge';
import type { Product } from '@/lib/types/product';

const COLUMNS = [
  { label: 'ID' },
  { label: 'Foto' },
  { label: 'Descrição' },
  { label: 'Preço' },
  { label: 'Qtde (Estoque)' },
  { label: 'Ações' },
];

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

  const renderForm = (
    item: Product | null | undefined,
    onClose: () => void,
  ) => (
    <ProductForm
      key={item?.id ?? 'new'}
      isEditing={!!item}
      isPending={isPending}
      categories={categories}
      defaultValues={
        item
          ? {
              descricao: item.descricao,
              preco: item.preco,
              foto: item.foto,
              quantidade: item.quantidade,
              categoriaId: item.categoriaId,
            }
          : undefined
      }
      onSubmit={(data) => {
        if (item) {
          updateProduct({ id: item.id, data });
        } else {
          createProduct(data);
        }
        onClose();
      }}
      onCancel={onClose}
    />
  );

  const handleDelete = (product: Product) => deleteProduct(product.id);

  return (
    <AdminCrudPage<Product>
      title="Gerenciamento de Produtos"
      items={products}
      isLoading={isLoading}
      columns={COLUMNS}
      addLabel="+ Novo produto"
      getFormTitle={getFormTitle}
      renderForm={renderForm}
      renderRow={renderRow}
      getDeleteDescription={getDeleteDescription}
      onDelete={handleDelete}
      deleteConfirmLabel="Excluir"
    />
  );
};

const getFormTitle = (item: Product | null | undefined) =>
  item ? 'Alterar Produto' : 'Inserir novo produto';

const getDeleteDescription = (product: Product) => (
  <>
    Deseja excluir{' '}
    <span className="text-white font-medium">"{product.descricao}"</span>? Esta
    ação não pode ser desfeita.
  </>
);

const renderRow = (
  product: Product,
  onEdit: ((item: Product) => void) | undefined,
  onDelete: ((item: Product) => void) | undefined,
) => (
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
      {onEdit && (
        <Button
          size="icon"
          className="bg-violet-600 hover:bg-violet-500 text-white"
          onClick={() => onEdit(product)}
          title="Alterar"
        >
          <Pencil className="w-4 h-4" />
        </Button>
      )}
      {onDelete && (
        <Button
          size="icon"
          variant="destructive"
          onClick={() => onDelete(product)}
          title="Excluir"
        >
          <Trash2 className="w-4 h-4" />
        </Button>
      )}
    </TableCell>
  </TableRow>
);

export default Produtos;
