import { TableCell, TableRow } from '@/components/ui/table';
import { useShopping } from '@/lib/hooks/use-shopping-client';
import type { Compras } from '@/lib/services/compras.services';

import { AdminCrudPage } from '../components/admin-crud-page';

const COLUMNS = [
  { label: 'ID' },
  { label: 'Nome do cliente' },
  { label: 'Quantidade de compras' },
];

const ComprasCLiente = () => {
  const { shopping, isLoading } = useShopping();

  return (
    <AdminCrudPage<Compras>
      title="Total compras por cliente"
      items={shopping}
      isLoading={isLoading}
      columns={COLUMNS}
      renderRow={renderRow}
    />
  );
};

const renderRow = (Compras: Compras) => (
  <TableRow key={Compras.id}>
    <TableCell className="font-medium">{Compras.id}</TableCell>
    <TableCell>{Compras.nomeCliente}</TableCell>
    <TableCell>{Compras.qtdCompras}</TableCell>
  </TableRow>
);

export default ComprasCLiente;
