import { Trash2 } from 'lucide-react';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
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
import { useSalesAdm } from '@/lib/hooks/use-sales';
import type { Sale } from '@/lib/services/sales.service';
import { linkify } from '@/lib/utils/linkify';

import { AdminCrudPage } from '../components/admin-crud-page';

const COLUMNS = [
  { label: 'ID' },
  { label: 'Data e hora' },
  { label: 'ID de usuário' },
  { label: 'Total' },
  { label: 'Ações', className: 'text-right' },
];

const Vendas = () => {
  const [selectedSale, setSelectedSale] = useState<Sale | null>(null);
  const { sales, isLoading, deleteSale } = useSalesAdm();

  const renderRow = (
    venda: Sale,
    _onEdit: ((item: Sale) => void) | undefined,
    onDelete: ((item: Sale) => void) | undefined,
  ) => (
    <TableRow key={venda.id}>
      <TableCell className="font-medium">{venda.id}</TableCell>
      <TableCell>{venda.dataHora}</TableCell>
      <TableCell>{venda.userId}</TableCell>
      <TableCell>{venda.total}</TableCell>
      <TableCell>
        <div className="flex justify-end gap-2">
          <Button variant="outline" onClick={() => setSelectedSale(venda)}>
            Ver itens
          </Button>
          {onDelete && (
            <Button
              size="icon"
              variant="destructive"
              onClick={() => {
                onDelete(venda);
              }}
              title="Excluir"
            >
              <Trash2 className="w-4 h-4" />
            </Button>
          )}
        </div>
      </TableCell>
    </TableRow>
  );

  return (
    <>
      <AdminCrudPage<Sale>
        title="Gerenciamento de vendas"
        items={sales}
        isLoading={isLoading}
        columns={COLUMNS}
        renderRow={renderRow}
        getDeleteDescription={getDeleteDescription}
        onDelete={(venda) => deleteSale(venda.id)}
      />
      {selectedSale && (
        <Dialog open onOpenChange={(open) => !open && setSelectedSale(null)}>
          <DialogContent className="sm:max-w-xl">
            <DialogHeader>
              <DialogTitle>Venda #{selectedSale.id}</DialogTitle>
            </DialogHeader>

            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Produto</TableHead>
                  <TableHead>Qtd</TableHead>
                  <TableHead className="text-right">Preço</TableHead>
                </TableRow>
              </TableHeader>

              <TableBody>
                {selectedSale.items.map((item) => (
                  <TableRow key={item.productId}>
                    <TableCell className="flex items-center gap-3">
                      {item.product?.foto && (
                        <img
                          src={linkify(item.product.foto)}
                          alt={item.product.descricao}
                          className="h-10 w-10 rounded object-cover"
                        />
                      )}

                      {item.product?.descricao}
                    </TableCell>

                    <TableCell>{item.quantity}</TableCell>

                    <TableCell className="text-right">{item.price}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </DialogContent>
        </Dialog>
      )}
    </>
  );
};

const getDeleteDescription = (venda: Sale) => (
  <span>
    Excluir a venda <strong>{venda.id}</strong>?
  </span>
);

export default Vendas;
