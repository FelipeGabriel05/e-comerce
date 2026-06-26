import { Link as LinkRouter } from '@tanstack/react-router';
import { ArrowLeft, Trash } from 'lucide-react';
import { useState } from 'react';

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { useCart } from '@/lib/hooks/use-cart';
import { api } from '@/lib/services/constants';

const currencyFormatter = new Intl.NumberFormat('pt-BR', {
  style: 'currency',
  currency: 'BRL',
});

const formatCurrency = (value: number) => currencyFormatter.format(value);

const CarrinhoPage = () => {
  const { cart, updateCart, removeFromCart } = useCart();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleFinalizarCompra = async () => {
    if (!confirm('Deseja finalizar a compra?')) return;
    setIsSubmitting(true);
    try {
      await api.post('/sales');
    } catch (error) {
      console.error('Erro ao finalizar compra:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  if (!cart || cart.items.length === 0) {
    return (
      <div className="flex justify-center py-10">
        <div className="w-full max-w-6xl rounded-xl bg-indigo-900/80 p-8 shadow-2xl">
          <h1 className="mb-4 text-3xl font-bold text-white">
            Carrinho de Compras
          </h1>
          <LinkRouter
            to="/"
            className="mb-6 flex items-center gap-2 text-white/70 hover:text-white"
          >
            <ArrowLeft size={16} />
            Voltar para Página Inicial
          </LinkRouter>
          <p className="text-white/70">Seu carrinho está vazio.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="flex justify-center py-10">
      <div className="w-full max-w-6xl rounded-xl bg-indigo-900/80 p-8 shadow-2xl">
        <h1 className="mb-2 text-3xl font-bold text-white">
          Carrinho de Compras
        </h1>

        <LinkRouter
          to="/"
          className="mb-6 flex items-center gap-2 text-white/70 hover:text-white"
        >
          <ArrowLeft size={16} />
          Voltar para Página Inicial
        </LinkRouter>

        <Table>
          <TableHeader className="bg-indigo-950">
            <TableRow>
              <TableHead className="text-zinc-100">Produto</TableHead>
              <TableHead className="text-zinc-100">Preço Unit.</TableHead>
              <TableHead className="text-zinc-100">Quantidade</TableHead>
              <TableHead className="text-right text-zinc-100">
                Total Item
              </TableHead>
              <TableHead className="text-right text-zinc-100">Ações</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody className="bg-indigo-800">
            {cart.items.map((item) => (
              <TableRow key={item.product.id}>
                <TableCell className="flex items-center gap-3 font-medium text-white">
                  {item.product.foto ? (
                    <img
                      src={item.product.foto}
                      alt={item.product.descricao}
                      className="h-12 w-12 rounded-md object-cover"
                    />
                  ) : (
                    <div className="flex h-12 w-12 items-center justify-center rounded-md bg-indigo-700 text-white/30 text-xs">
                      Sem foto
                    </div>
                  )}
                  {item.product.descricao}
                </TableCell>

                <TableCell className="text-white">
                  {formatCurrency(item.product.preco)}
                </TableCell>

                <TableCell className="text-white">
                  <div className="flex items-center gap-2">
                    <button
                      type="button"
                      onClick={() =>
                        updateCart(
                          item.product.id,
                          Math.max(1, item.quantity - 1),
                        )
                      }
                      className="flex h-7 w-7 items-center justify-center rounded bg-indigo-700 text-white hover:bg-indigo-600"
                    >
                      -
                    </button>
                    <span className="w-6 text-center">{item.quantity}</span>
                    <button
                      type="button"
                      onClick={() =>
                        updateCart(item.product.id, item.quantity + 1)
                      }
                      className="flex h-7 w-7 items-center justify-center rounded bg-indigo-700 text-white hover:bg-indigo-600"
                    >
                      +
                    </button>
                  </div>
                </TableCell>

                <TableCell className="text-right text-white">
                  {formatCurrency(item.subtotal)}
                </TableCell>

                <TableCell>
                  <div className="flex justify-end">
                    <button
                      type="button"
                      onClick={() => {
                        if (
                          confirm(
                            `Remover "${item.product.descricao}" do carrinho?`,
                          )
                        ) {
                          removeFromCart(item.product.id);
                        }
                      }}
                      className="flex h-10 w-10 items-center justify-center rounded-md bg-red-600 hover:bg-red-800 text-white"
                    >
                      <Trash size={16} />
                    </button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>

        <div className="mt-8 rounded-xl bg-indigo-800/60 backdrop-blur-md p-6">
          <h2 className="mb-4 text-center text-xl font-bold text-white">
            Resumo do Pedido
          </h2>
          <div className="flex flex-col items-end gap-1 text-white/80">
            <p>Subtotal: {formatCurrency(cart.total)}</p>
          </div>
          <div className="mt-2 flex justify-end">
            <p className="text-2xl font-bold text-white">
              Total Geral: {formatCurrency(cart.total)}
            </p>
          </div>

          <button
            type="button"
            onClick={handleFinalizarCompra}
            disabled={isSubmitting}
            className="mt-6 h-12 w-full rounded-md bg-emerald-600 text-lg font-bold text-white hover:bg-emerald-500 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {isSubmitting ? 'Finalizando...' : 'Finalizar Compra'}
          </button>
          <p className="mt-2 text-center text-sm text-white/60">
            ⚠ Requer sessão de usuário ativa (cliente logado)
          </p>
        </div>
      </div>
    </div>
  );
};

export default CarrinhoPage;
