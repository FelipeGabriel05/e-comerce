import { api } from './constants';

interface EstoqueResponse {
  status: number;
  code: string;
  message: string;
  data: Array<faltaEstoque>;
}

export interface faltaEstoque {
  id: number;
  descricao: string;
  preco: number;
}

export const fetchStock = async (): Promise<Array<faltaEstoque>> => {
  const response = await api.get<EstoqueResponse>('/estoque');
  return response.data.data;
};
