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
  const response = await api.get<ComprasResponse>('/total-compras');
  return response.data.data;
};
