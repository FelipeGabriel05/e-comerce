import { z } from 'zod';

export const ExportReportSchema = z.object({
  startDate: z.string().min(1, 'Informe a data inicial'),
  endDate: z.string().min(1, 'Informe a data final'),
  format: z.enum(['csv', 'pdf', 'html']),
});

export type ExportReportData = z.infer<typeof ExportReportSchema>;
