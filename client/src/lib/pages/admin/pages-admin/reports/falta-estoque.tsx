import { TableCell, TableRow } from '@/components/ui/table';
import { useStock } from '@/lib/hooks/use-report-stock';
import type { faltaEstoque } from '@/lib/services/report-stock.service';

import { AdminCrudPage } from '../../components/admin-crud-page';

const COLUMNS = [{ label: 'ID' }, { label: 'Descrição' }, { label: 'Preço' }];

const FaltaEstoque = () => {
  const { stock, isLoading } = useStock();

  return (
    <AdminCrudPage<faltaEstoque>
      title="Total compras por cliente"
      items={stock}
      isLoading={isLoading}
      columns={COLUMNS}
      renderRow={renderRow}
    />
  );
};

const renderRow = (estoque: faltaEstoque) => (
  <TableRow key={estoque.id}>
    <TableCell className="font-medium">{estoque.id}</TableCell>
    <TableCell>{estoque.descricao}</TableCell>
    <TableCell>{estoque.preco}</TableCell>
  </TableRow>
);

export default FaltaEstoque;
