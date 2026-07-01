import { TableCell, TableRow } from '@/components/ui/table';
import { useDaily } from '@/lib/hooks/use-daily-rate';
import type { Valor } from '@/lib/services/valor-dia.services';

import { AdminCrudPage } from '../components/admin-crud-page';

const COLUMNS = [
  { label: 'ID' },
  { label: 'Data' },
  { label: 'Valor monetário' },
];

const ValorRecebido = () => {
  const { daily, isLoading } = useDaily();
  return (
    <AdminCrudPage<Valor>
      title="Total compras por cliente"
      items={daily}
      isLoading={isLoading}
      columns={COLUMNS}
      renderRow={renderRow}
    />
  );
};

const renderRow = (ValorDia: Valor) => (
  <TableRow key={ValorDia.id}>
    <TableCell className="font-medium">{ValorDia.id}</TableCell>
    <TableCell>{ValorDia.data}</TableCell>
    <TableCell>{ValorDia.valor}</TableCell>
  </TableRow>
);

export default ValorRecebido;
