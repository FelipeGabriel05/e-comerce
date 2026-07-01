import { Link as LinkRouter } from '@tanstack/react-router';
import { ArrowLeft } from 'lucide-react';

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { H1, P } from '@/components/ui/typography';
import { useSales } from '@/lib/hooks/use-sales';

const currencyFormatter = new Intl.NumberFormat('pt-BR', {
  style: 'currency',
  currency: 'BRL',
});

const formatCurrency = (value: number) => currencyFormatter.format(value);

const formatDate = (dateStr: string) =>
  new Date(dateStr).toLocaleDateString('pt-BR');

const HistoricoPedidosPage = () => {
  const { sales, isLoading } = useSales();

  return (
    <div className="flex justify-center py-10">
      <div className="w-full max-w-5xl flex flex-col gap-6">
        <div className="rounded-xl bg-white/5 border border-white/10 backdrop-blur-md overflow-hidden">
          <div className="flex items-center justify-between px-6 py-5 border-b border-white/10">
            <div className="flex flex-col gap-0.5">
              <LinkRouter
                to="/cliente"
                className="flex items-center gap-2 text-white/70 hover:text-white text-sm"
              >
                <ArrowLeft size={16} />
                Voltar para Área do Cliente
              </LinkRouter>
              <H1 className="text-white">Histórico de Compras</H1>
              <p className="text-xs text-white/40">
                {sales.length} {sales.length === 1 ? 'pedido' : 'pedidos'}
              </p>
            </div>
          </div>

          {isLoading ? (
            <div className="py-16 text-center text-white/30 text-sm">
              Carregando...
            </div>
          ) : sales.length === 0 ? (
            <div className="py-16 text-center">
              <P className="text-white/50">Nenhum pedido encontrado.</P>
            </div>
          ) : (
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Data</TableHead>
                  <TableHead>Nº do Pedido</TableHead>
                  <TableHead>Status</TableHead>
                  <TableHead className="text-right">Total Geral</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {sales.map((sale) => (
                  <TableRow key={sale.id}>
                    <TableCell>{formatDate(sale.dataHora)}</TableCell>
                    <TableCell>{sale.id}</TableCell>
                    <TableCell>
                      <span className="rounded-full bg-emerald-600/20 px-2 py-1 text-xs font-medium text-emerald-400">
                        Entregue
                      </span>
                    </TableCell>
                    <TableCell className="text-right">
                      {formatCurrency(sale.total)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </div>
      </div>
    </div>
  );
};

export default HistoricoPedidosPage;
