import { api } from './constants';

interface valorDiaResponse {
  status: number;
  code: string;
  message: string;
  data: Array<Valor>;
}

export interface Valor {
  id: number;
  data: string;
  valor: number;
}

export const fetchDaily = async (): Promise<Array<Valor>> => {
  const response = await api.get<valorDiaResponse>('/valor-dia');
  return response.data.data;
};
