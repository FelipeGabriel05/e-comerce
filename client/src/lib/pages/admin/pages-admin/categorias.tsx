import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { useCategoria } from '@/lib/hooks/use-categoria';

import Layout from '../layout-sidebar';
import DialogCategoria from './dialogs-categoria/dialog-categoria';
import DialogDelete from './dialogs-categoria/dialog-delete';
import DialogEdit from './dialogs-categoria/dialog-edit';

const Categorias = () => {
  const categorias = useCategoria();
  type Categoria = {
    id: number;
    NomeCategoria: string;
  };
  return (
    <Layout>
      <div className="flex justify-center py-10">
        <div className="w-full max-w-6xl rounded-xl bg-indigo-900/80 p-8 shadow-2xl">
          <h1 className="mb-4 text-3xl font-bold text-white">
            Gerenciamento de categorias
          </h1>
          <Table>
            <TableHeader className="bg-indigo-950">
              <TableRow>
                <TableHead className="text-zinc-100">ID</TableHead>

                <TableHead className="text-zinc-100">
                  Nome da Categoria
                </TableHead>

                <TableHead className="text-right text-zinc-100">
                  Ações
                </TableHead>
              </TableRow>
            </TableHeader>

            <TableBody className="bg-indigo-800">
              {categorias.map((categoria: Categoria) => (
                <TableRow key={categoria.id}>
                  <TableCell className="font-medium text-white">
                    {categoria.id}
                  </TableCell>

                  <TableCell className="text-white">
                    {categoria.NomeCategoria}
                  </TableCell>

                  <TableCell>
                    <div className="flex justify-end gap-2">
                      <DialogEdit categoria={categoria} />
                      <DialogDelete categoria={categoria} />
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          <div className="mt-8">
            <DialogCategoria />
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Categorias;
