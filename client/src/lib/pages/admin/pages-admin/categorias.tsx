import { Pencil, Trash2 } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { TableCell, TableRow } from '@/components/ui/table';
import { useCategories } from '@/lib/hooks/use-categories';
import type { Category } from '@/lib/services/categories.service';

import { AdminCrudPage } from '../components/admin-crud-page';
import { CategoriaForm } from './forms/categoria-form';

const COLUMNS = [
  { label: 'ID' },
  { label: 'Nome da Categoria' },
  { label: 'Ações', className: 'text-right' },
];

const Categorias = () => {
  const { categories, isLoading, deleteCategory } = useCategories();

  return (
    <AdminCrudPage<Category>
      title="Gerenciamento de categorias"
      items={categories}
      isLoading={isLoading}
      columns={COLUMNS}
      renderRow={renderRow}
      renderForm={(item, onClose) => (
        <CategoriaForm item={item} onClose={onClose} />
      )}
      getFormTitle={getFormTitle}
      getDeleteDescription={getDeleteDescription}
      onDelete={(categoria) => deleteCategory(categoria.id)}
    />
  );
};

const getFormTitle = (item: Category | null | undefined) =>
  item ? 'Editar categoria' : 'Nova categoria';

const getDeleteDescription = (categoria: Category) => (
  <span>
    Excluir a categoria <strong>{categoria.descricao}</strong>?
  </span>
);

const renderRow = (
  categoria: Category,
  onEdit: ((item: Category) => void) | undefined,
  onDelete: ((item: Category) => void) | undefined,
) => (
  <TableRow key={categoria.id}>
    <TableCell className="font-medium">{categoria.id}</TableCell>
    <TableCell>{categoria.descricao}</TableCell>
    <TableCell>
      <div className="flex justify-end gap-2">
        {onEdit && (
          <Button
            size="icon"
            className="bg-violet-600 hover:bg-violet-500 text-white"
            onClick={() => onEdit(categoria)}
            title="Alterar"
          >
            <Pencil className="w-4 h-4" />
          </Button>
        )}
        {onDelete && (
          <Button
            size="icon"
            variant="destructive"
            onClick={() => onDelete(categoria)}
            title="Excluir"
          >
            <Trash2 className="w-4 h-4" />
          </Button>
        )}
      </div>
    </TableCell>
  </TableRow>
);

export default Categorias;
