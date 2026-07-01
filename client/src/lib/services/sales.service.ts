import { api } from './constants';

interface SalesResponse {
  status: number;
  code: string;
  message: string;
  data: Array<Sale>;
}

export interface Sale {
  id: number;
  dataHora: string;
  total: number;
}

export const fetchSales = async (): Promise<Array<Sale>> => {
  const response = await api.get<SalesResponse>('/admin/sales');
  return response.data.data;
};

export async function deleteSaleService(id: number) {
  const response = await api.delete(`/admin/sales/${id}`);
  return response.data;
}
