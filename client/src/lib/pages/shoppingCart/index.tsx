import { Link as LinkRouter } from '@tanstack/react-router';
import { ArrowLeft, Minus, Plus, ShoppingCart, Trash2 } from 'lucide-react';
import { useState } from 'react';

import { Button } from '@/components/ui/button';
import { ConfirmDialog } from '@/components/ui/confirm-dialog';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { H1, P } from '@/components/ui/typography';
import { useCart } from '@/lib/hooks/use-cart';
import { useFinalizeCart } from '@/lib/hooks/use-finalize-cart';
import type { CartItem } from '@/lib/services/cart.service';

function linkify(foto: string): string {
  if (!foto) return '';
  if (foto.startsWith('http://') || foto.startsWith('https://')) {
    return foto;
  }
  const base = import.meta.env.VITE_API_BASE_URL;
  const path = foto.startsWith('/') ? foto : `/${foto}`;
  return `${base}/image${path}`;
}

const currencyFormatter = new Intl.NumberFormat('pt-BR', {
  style: 'currency',
  currency: 'BRL',
});

const formatCurrency = (value: number) => currencyFormatter.format(value);

const CarrinhoPage = () => {
  const { cart, updateCart, removeFromCart } = useCart();
  const { finalizeCart, isSubmitting } = useFinalizeCart();
  const [itemToRemove, setItemToRemove] = useState<CartItem | null>(null);
  const [confirmFinalize, setConfirmFinalize] = useState(false);

  if (!cart || cart.items.length === 0) {
    return (
      <div className="flex justify-center py-10">
        <div className="w-full max-w-6xl rounded-xl bg-white/5 border border-white/10 backdrop-blur-md p-8">
          {' '}
          <H1 className="mb-4 text-white">Carrinho de Compras</H1>
          <LinkRouter
            to="/"
            className="mb-2 flex items-center gap-2 text-white/70 hover:text-white text-sm"
          >
            <ArrowLeft size={16} />
            Voltar para Página Inicial
          </LinkRouter>
          <H1 className="mb-4 text-white">Carrinho de Compras</H1>
          <P className="text-white/70">Seu carrinho está vazio.</P>
        </div>
      </div>
    );
  }

  return (
    <div className="flex justify-center py-10">
      <div className="w-full max-w-6xl flex flex-col gap-6">
        <div className="rounded-xl bg-white/5 border border-white/10 backdrop-blur-md overflow-hidden">
          <div className="flex items-center justify-between px-6 py-5 border-b border-white/10">
            <div className="flex flex-col gap-0.5">
              <LinkRouter
                to="/"
                className="flex items-center gap-2 text-white/70 hover:text-white text-sm"
              >
                <ArrowLeft size={16} />
                Voltar para Página Inicial
              </LinkRouter>
              <H1 className="text-white">Carrinho de Compras</H1>
              <p className="text-xs text-white/40">
                {cart.items.length} {cart.items.length === 1 ? 'item' : 'itens'}
              </p>
            </div>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Produto</TableHead>
                <TableHead>Preço Unit.</TableHead>
                <TableHead>Quantidade</TableHead>
                <TableHead className="text-right">Total Item</TableHead>
                <TableHead className="text-right">Ações</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {cart.items.map((item) => (
                <TableRow key={item.product.id}>
                  <TableCell className="flex items-center gap-3 font-medium">
                    {item.product.foto ? (
                      <img
                        src={linkify(item.product.foto)}
                        alt={item.product.descricao}
                        className="h-12 w-12 rounded-md object-cover"
                      />
                    ) : (
                      <div className="flex h-12 w-12 items-center justify-center rounded-md bg-white/10 text-white/30 text-xs">
                        Sem foto
                      </div>
                    )}
                    {item.product.descricao}
                  </TableCell>

                  <TableCell>{formatCurrency(item.product.preco)}</TableCell>

                  <TableCell>
                    <div className="flex items-center gap-2">
                      <Button
                        size="icon-sm"
                        variant="outline"
                        type="button"
                        onClick={() =>
                          updateCart(
                            item.product.id,
                            Math.max(1, item.quantity - 1),
                          )
                        }
                      >
                        <Minus className="w-3 h-3" />
                      </Button>
                      <span className="w-6 text-center">{item.quantity}</span>
                      <Button
                        size="icon-sm"
                        variant="outline"
                        type="button"
                        onClick={() =>
                          updateCart(item.product.id, item.quantity + 1)
                        }
                      >
                        <Plus className="w-3 h-3" />
                      </Button>
                    </div>
                  </TableCell>

                  <TableCell className="text-right">
                    {formatCurrency(item.subtotal)}
                  </TableCell>

                  <TableCell>
                    <div className="flex justify-end">
                      <Button
                        size="icon"
                        variant="destructive"
                        type="button"
                        onClick={() => setItemToRemove(item)}
                        title="Remover"
                      >
                        <Trash2 className="w-4 h-4" />
                      </Button>
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>

        <div className="rounded-xl bg-white/5 border border-white/10 backdrop-blur-md p-6">
          <H1 className="mb-4 text-center text-white">Resumo do Pedido</H1>
          <div className="flex flex-col items-end gap-1 text-white/80">
            <P>Subtotal: {formatCurrency(cart.total)}</P>
          </div>
          <div className="mt-2 flex justify-end">
            <P className="text-2xl font-bold text-white">
              Total Geral: {formatCurrency(cart.total)}
            </P>
          </div>

          <Button
            type="button"
            onClick={() => setConfirmFinalize(true)}
            disabled={isSubmitting}
            className="mt-6 h-12 w-full bg-emerald-600 text-lg font-bold text-white hover:bg-emerald-500 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <ShoppingCart className="w-5 h-5" />
            {isSubmitting ? 'Finalizando...' : 'Finalizar Compra'}
          </Button>
          <P className="mt-2 text-center text-sm text-white/60">
            ⚠ Requer sessão de usuário ativa (cliente logado)
          </P>
        </div>
      </div>

      {itemToRemove && (
        <ConfirmDialog
          title="Remover item"
          description={
            <span>
              Deseja remover <strong>{itemToRemove.product.descricao}</strong>{' '}
              do carrinho?
            </span>
          }
          confirmLabel="Remover"
          onConfirm={() => {
            removeFromCart(itemToRemove.product.id);
            setItemToRemove(null);
          }}
          onCancel={() => setItemToRemove(null)}
        />
      )}

      {confirmFinalize && (
        <ConfirmDialog
          title="Finalizar compra"
          description="Deseja confirmar a finalização da compra?"
          confirmLabel="Confirmar"
          onConfirm={() => {
            setConfirmFinalize(false);
            finalizeCart();
          }}
          onCancel={() => setConfirmFinalize(false)}
        />
      )}
    </div>
  );
};

export default CarrinhoPage;
