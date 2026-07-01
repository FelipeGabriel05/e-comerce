import { Button, Input } from '@base-ui/react';
import { zodResolver } from '@hookform/resolvers/zod';
import { Controller, useForm } from 'react-hook-form';

import { Field, FieldGroup, FieldLabel } from '@/components/ui/field';
import { exportShoppingReport } from '@/lib/services/report-shopping.services';

import {
  type ExportReportData,
  ExportReportSchema,
} from './schemas/export-report-schema';

export function ExportReportForm({ onClose }: { onClose: () => void }) {
  const form = useForm<ExportReportData>({
    resolver: zodResolver(ExportReportSchema),
    defaultValues: {
      startDate: '',
      endDate: '',
      format: 'pdf',
    },
  });

  async function onSubmit(data: ExportReportData) {
    await exportShoppingReport(data);
    onClose();
  }

  return (
    <form
      className="flex flex-col gap-6"
      onSubmit={form.handleSubmit(onSubmit)}
    >
      <FieldGroup>
        {/* Data inicial */}

        <Controller
          control={form.control}
          name="startDate"
          render={({ field }) => (
            <Field>
              <FieldLabel>Data inicial</FieldLabel>

              <Input type="date" {...field} />
            </Field>
          )}
        />

        {/* Data final */}

        <Controller
          control={form.control}
          name="endDate"
          render={({ field }) => (
            <Field>
              <FieldLabel>Data final</FieldLabel>

              <Input type="date" {...field} />
            </Field>
          )}
        />

        {/* Formato */}

        <Controller
          control={form.control}
          name="format"
          render={({ field }) => (
            <Field>
              <FieldLabel>Formato</FieldLabel>

              <select
                {...field}
                className="border rounded-md h-10 px-3 bg-background"
              >
                <option value="pdf">PDF</option>
                <option value="csv">CSV</option>
                <option value="html">HTML</option>
              </select>
            </Field>
          )}
        />

        <Button type="submit" className="bg-violet-600 hover:bg-violet-500">
          Baixar relatório
        </Button>
      </FieldGroup>
    </form>
  );
}
