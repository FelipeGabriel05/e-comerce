import { type ReactNode, useState } from 'react';

import { Button } from '@/components/ui/button';
import { ConfirmDialog } from '@/components/ui/confirm-dialog';
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

import Layout from '../layout-sidebar';

type Column = {
  label: string;
  className?: string;
};

type AdminCrudPageProps<T> = {
  title: string;
  items: Array<T>;
  isLoading?: boolean;
  columns: Array<Column>;
  renderRow: (
    item: T,
    onEdit: ((item: T) => void) | undefined,
    onDelete: ((item: T) => void) | undefined,
  ) => ReactNode;
  renderForm?: (item: T | null, onClose: () => void) => ReactNode;
  getFormTitle?: (item: T | null) => string;
  getDeleteDescription?: (item: T) => ReactNode;
  onDelete?: (item: T) => void;
  addLabel?: string;
  deleteConfirmLabel?: string;
  headerActions?: ReactNode;
  showEdit?: boolean;
  showDelete?: boolean;
};

const CARD =
  'rounded-xl bg-white/5 border border-white/10 backdrop-blur-md overflow-hidden';
const HEADER =
  'flex items-center justify-between px-6 py-5 border-b border-white/10 bg-gradient-to-r from-[oklch(0.62_0.23_290/8%)] to-transparent';

export function AdminCrudPage<T>({
  title,
  items,
  isLoading,
  columns,
  renderRow,
  renderForm,
  getFormTitle,
  getDeleteDescription,
  onDelete,
  addLabel = '+ Inserir novo',
  deleteConfirmLabel = 'Excluir',
  headerActions,
  showEdit = true,
  showDelete = true,
}: AdminCrudPageProps<T>) {
  const [editing, setEditing] = useState<T | null>(null);
  const [formOpen, setFormOpen] = useState(false);
  const [deleting, setDeleting] = useState<T | null>(null);

  const openCreate = () => {
    setEditing(null);
    setFormOpen(true);
  };

  const closeForm = () => {
    setFormOpen(false);
    setEditing(null);
  };

  if (isLoading) {
    return (
      <Layout>
        <div className={CARD}>
          <div className={HEADER}>
            <div className="h-5 w-48 rounded bg-white/10 animate-pulse" />
          </div>
          <div className="py-16 text-center text-white/30 text-sm">
            Carregando...
          </div>
        </div>
      </Layout>
    );
  }

  return (
    <Layout>
      {renderForm && (
        <Dialog
          open={formOpen}
          onOpenChange={(open) => {
            if (!open) closeForm();
          }}
        >
          <DialogContent className="sm:max-w-lg overflow-y-auto max-h-[90vh]">
            <DialogHeader>
              <DialogTitle>{getFormTitle?.(editing)}</DialogTitle>
            </DialogHeader>
            {renderForm(editing, closeForm)}
          </DialogContent>
        </Dialog>
      )}

      <div className={CARD}>
        <div className={HEADER}>
          <div className="flex flex-col gap-0.5">
            <h1 className="text-lg font-semibold text-white">{title}</h1>
            <p className="text-xs text-white/40">
              {items.length} {items.length === 1 ? 'item' : 'itens'}
            </p>
          </div>
          <div className="flex items-center gap-2">
            {headerActions}
            {renderForm && (
              <Button
                size="sm"
                className="bg-violet-600 hover:bg-violet-500 text-white"
                onClick={openCreate}
              >
                {addLabel}
              </Button>
            )}
          </div>
        </div>

        <Table>
          <TableHeader>
            <TableRow>
              {columns.map((col) => (
                <TableHead key={col.label} className={col.className}>
                  {col.label}
                </TableHead>
              ))}
            </TableRow>
          </TableHeader>
          <TableBody>
            {items.length === 0 ? (
              <TableRow>
                <TableCell
                  colSpan={columns.length}
                  className="py-16 text-center text-white/30 text-sm"
                >
                  Nenhum item cadastrado.
                </TableCell>
              </TableRow>
            ) : (
              items.map((item) =>
                renderRow(
                  item,
                  showEdit
                    ? (it) => {
                        setEditing(it);
                        setFormOpen(true);
                      }
                    : undefined,
                  showDelete ? setDeleting : undefined,
                ),
              )
            )}
          </TableBody>
        </Table>
      </div>

      {deleting && onDelete && getDeleteDescription && (
        <ConfirmDialog
          title="Confirmar exclusão"
          description={getDeleteDescription(deleting)}
          confirmLabel={deleteConfirmLabel}
          onConfirm={() => {
            onDelete(deleting);
            setDeleting(null);
          }}
          onCancel={() => setDeleting(null)}
        />
      )}
    </Layout>
  );
}
