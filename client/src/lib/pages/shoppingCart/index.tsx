import { Button } from '@base-ui/react/button';
import { Link } from '@tanstack/react-router';
import { Trash } from 'lucide-react';

import {
  Table,
  TableBody,
  TableCell,
  TableFooter,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';

// lógica dos produtos do carrinho
const produtos = [
  {
    id: 1,
    Produto: 'GTA V - PS5',
    Preco_uni: '$ 249,90',
    Quantidade: '1',
    totalItem: '249,90',
  },
  {
    id: 2,
    Produto: 'God of War - PS5',
    Preco_uni: '$ 249,90',
    Quantidade: '1',
    totalItem: '249,90',
  },
  {
    id: 3,
    Produto: 'FIFA 23 - PS5',
    Preco_uni: '$ 249,90',
    Quantidade: '1',
    totalItem: '249,90',
  },
  {
    id: 4,
    Produto: 'Forza Horizon 5 - XBOX',
    Preco_uni: '$ 249,90',
    Quantidade: '1',
    totalItem: '249,90',
  },
  {
    id: 5,
    Produto: 'GTA V',
    Preco_uni: '$ 249,90',
    Quantidade: '1',
    totalItem: '249,90',
  },
  {
    id: 6,
    Produto: 'GTA V',
    Preco_uni: '$ 249,90',
    Quantidade: '1',
    totalItem: '249,90',
  },
  {
    id: 7,
    Produto: 'GTA V',
    Preco_uni: '$ 249,90',
    Quantidade: '1',
    totalItem: '249,90',
  },
];

const Carrinho = () => {
  return (
    <div className="flex justify-center py-10">
      <div className="w-full max-w-6xl rounded-xl bg-indigo-900/80 p-8 shadow-2xl">
        <h1 className="mb-4 text-3xl font-bold text-white">
          Carrinho de Compras
        </h1>

        <div className="mb-6">
          <Link to="/" className="text-indigo-300 hover:text-indigo-100">
            Voltar para Página Inicial
          </Link>
        </div>

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
            {produtos.map((id) => (
              <TableRow key={id.id}>
                <TableCell className="font-medium text-white">
                  {id.Produto}
                </TableCell>

                <TableCell className="text-white">{id.Preco_uni}</TableCell>

                <TableCell className="text-white">{id.Quantidade}</TableCell>

                <TableCell className="text-right text-white">
                  {id.totalItem}
                </TableCell>

                <TableCell>
                  <div className="flex justify-end">
                    <Button className="flex h-10 w-10 items-center justify-center rounded-md bg-red-600 hover:bg-red-800">
                      <Trash />
                    </Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
          <TableFooter className="bg-indigo-950">
            <TableRow>
              <TableCell colSpan={3} className="text-white">
                Total
              </TableCell>

              <TableCell className="text-right text-white">$999.60</TableCell>
            </TableRow>
          </TableFooter>
        </Table>
        <div className="mt-8">
          <Button className="h-12 w-full rounded-md bg-emerald-600 text-lg font-bold hover:bg-emerald-500">
            Finalizar Compra
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Carrinho;
