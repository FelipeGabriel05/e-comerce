import { Button } from '@base-ui/react/button';
import { SquarePen, Trash } from 'lucide-react';

import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { Field, FieldGroup } from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';

import Layout from '../layout-sidebar';

// lógica dos produtos do carrinho
const categoria = [
  {
    id: 1,
    NomeCategoria: 'Categoria A',
  },
  {
    id: 2,
    NomeCategoria: 'Categoria B',
  },
  {
    id: 3,
    NomeCategoria: 'Categoria C',
  },
  {
    id: 4,
    NomeCategoria: 'Categoria D',
  },
  {
    id: 5,
    NomeCategoria: 'Categoria E',
  },
];

function DialogCategoria() {
  return (
    <Dialog>
      <form>
        <DialogTrigger
          render={
            <Button className="h-12 w-full rounded-md bg-emerald-600 text-lg font-bold hover:bg-emerald-500">
              Inserir nova categoria
            </Button>
          }
        />
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Nova categoria</DialogTitle>
            <DialogDescription>
              Cadastrar nova categoria de produto
            </DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="username-1">Nome da categoria</Label>
              <Input id="username-1" name="username" />
            </Field>
          </FieldGroup>
          <DialogFooter>
            <DialogClose>
              <Button>Cancelar</Button>
            </DialogClose>
            <Button type="submit">Inserir</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

type DialogEditProps = {
  categoria: {
    id: number;
    NomeCategoria: string;
  };
};

function DialogEdit({ categoria }: DialogEditProps) {
  return (
    <Dialog>
      <DialogTrigger
        render={
          <Button className="flex h-10 w-10 items-center justify-center rounded-md bg-blue-600 hover:bg-blue-800">
            <SquarePen />
          </Button>
        }
      />
      <form>
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Editar categoria</DialogTitle>
            <DialogDescription>Salva as novas alterações</DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="username-1">Nome da categoria</Label>
              <Input
                id="username-1"
                name="username"
                defaultValue={categoria.NomeCategoria}
              />
            </Field>
          </FieldGroup>
          <DialogFooter>
            <DialogClose>
              <Button>Cancelar</Button>
            </DialogClose>
            <Button type="submit">Salvar alteração</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

function DialogDelete({ categoria }: DialogEditProps) {
  return (
    <Dialog>
      <DialogTrigger
        render={
          <Button className="flex h-10 w-10 items-center justify-center rounded-md bg-red-600 hover:bg-red-800">
            <Trash />
          </Button>
        }
      />
      <form>
        <DialogContent className="sm:max-w-sm">
          <DialogHeader>
            <DialogTitle>Excluir categoria</DialogTitle>
            <DialogDescription>Salva as novas alterações</DialogDescription>
          </DialogHeader>
          <FieldGroup>
            <Field>
              <Label htmlFor="username-1">
                Tem certeza que deseja excluir?
              </Label>
              <Input
                id="username-1"
                name="username"
                defaultValue={categoria.NomeCategoria}
                readOnly
              />
            </Field>
          </FieldGroup>
          <DialogFooter>
            <DialogClose>
              <Button>Cancelar</Button>
            </DialogClose>
            <Button type="submit">Excluir</Button>
          </DialogFooter>
        </DialogContent>
      </form>
    </Dialog>
  );
}

const Categorias = () => {
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
              {categoria.map((id) => (
                <TableRow key={id.id}>
                  <TableCell className="font-medium text-white">
                    {id.id}
                  </TableCell>

                  <TableCell className="text-white">
                    {id.NomeCategoria}
                  </TableCell>

                  <TableCell>
                    <div className="flex justify-end gap-2">
                      <DialogEdit categoria={id} />
                      <DialogDelete categoria={id} />
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
