import { api } from './constants';

interface ComprasResponse {
  status: number;
  code: string;
  message: string;
  data: Array<Compras>;
}

export interface Compras {
  id: number;
  nomeCliente: string;
  qtdCompras: number;
}

export const fetchCompras = async (): Promise<Array<Compras>> => {
  const response = await api.get<ComprasResponse>('/admin/reports/daily-sales');
  return response.data.data;
};

export async function exportShoppingReport(data: {
  startDate: string;
  endDate: string;
  format: 'csv' | 'pdf' | 'html';
}) {
  const response = await api.get('/admin/reports/sales-by-customer/export', {
    params: data,
    responseType: 'blob',
  });

  const disposition = response.headers['content-disposition'];

  let fileName = `relatorio.${data.format}`;

  if (disposition) {
    const match = disposition.match(/filename="?(.+)"?/);

    if (match) {
      fileName = match[1];
    }
  }

  const blob = new Blob([response.data]);

  const url = window.URL.createObjectURL(blob);

  const link = document.createElement('a');

  link.href = url;
  link.download = fileName;

  document.body.appendChild(link);

  link.click();

  link.remove();

  window.URL.revokeObjectURL(url);
}
