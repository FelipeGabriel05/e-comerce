import { api } from './constants';

interface CategoriesResponse {
  status: number;
  code: string;
  message: string;
  data: Array<Category>;
}

export interface Category {
  id: string;
  descricao: string;
}

export const fetchCategories = async (): Promise<Array<Category>> => {
  const response = await api.get<CategoriesResponse>('/category');
  return response.data.data;
};
