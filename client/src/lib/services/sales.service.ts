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
}

export const fetchSales = async (): Promise<Array<Sale>> => {
  const response = await api.get<SalesResponse>('/sales');
  return response.data.data;
};

export async function deleteSaleService(id: number) {
  const response = await api.delete(`/sale/${id}`);
  return response.data;
}
