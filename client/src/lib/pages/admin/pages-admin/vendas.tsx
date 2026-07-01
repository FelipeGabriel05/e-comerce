import { Trash2 } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { TableCell, TableRow } from '@/components/ui/table';
import { useSales } from '@/lib/hooks/use-sales';
import type { Sale } from '@/lib/services/sales.service';

import { AdminCrudPage } from '../components/admin-crud-page';

const COLUMNS = [
  { label: 'ID' },
  { label: 'Data e hora' },
  { label: 'Ações', className: 'text-right' },
];

const Vendas = () => {
  const { sales, isLoading, deleteSale } = useSales();

  return (
    <AdminCrudPage<Sale>
      title="Gerenciamento de vendas"
      items={sales}
      isLoading={isLoading}
      columns={COLUMNS}
      renderRow={renderRow}
      getDeleteDescription={getDeleteDescription}
      onDelete={(categoria) => deleteSale(categoria.id)}
    />
  );
};

const getDeleteDescription = (venda: Sale) => (
  <span>
    Excluir a venda <strong>{venda.id}</strong>?
  </span>
);

const renderRow = (
  venda: Sale,
  onDelete: ((item: Sale) => void) | undefined,
) => (
  <TableRow key={venda.id}>
    <TableCell className="font-medium">{venda.id}</TableCell>
    <TableCell>{venda.dataHora}</TableCell>
    <TableCell>
      <div className="flex justify-end gap-2">
        {onDelete && (
          <Button
            size="icon"
            variant="destructive"
            onClick={() => onDelete(venda)}
            title="Excluir"
          >
            <Trash2 className="w-4 h-4" />
          </Button>
        )}
      </div>
    </TableCell>
  </TableRow>
);

export default Vendas;
