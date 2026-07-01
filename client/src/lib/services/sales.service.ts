import { api } from './constants';

interface SaleProduct {
  id: number;
  descricao: string;
  preco: number;
  foto: string;
  quantidade: number;
  categoriaId: number;
}

interface SaleItem {
  productId: number;
  price: number;
  quantity: number;
  product: SaleProduct;
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

export const fetchSale = async (): Promise<Array<Sale>> => {
  const response = await api.get<SalesResponse>('/admin/sales');
  return response.data.data;
};

export async function deleteSaleService(id: number) {
  const response = await api.delete(`/admin/sales/${id}`);
  return response.data;
}
