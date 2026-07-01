import { api } from './constants';

export interface SaleItem {
  productId: number;
  price: number;
  quantity: number;
}

export interface Sale {
  id: number;
  dataHora: string;
  userId: number;
  items: Array<SaleItem>;
  total: number;
}

interface SalesResponse {
  status: number;
  message: string;
  data: Array<Sale>;
}

export async function fetchSalesService(): Promise<Array<Sale>> {
  const response = await api.get<SalesResponse>('/sales');
  return response.data.data;
}
