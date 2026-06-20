import type { CategoriaFormData } from '../pages/admin/pages-admin/dialogs-categoria/schemas/categoria-schema';
import { api } from './constants';

export type Categoria = {
  id: number;
  descricao: string;
};

export type DialogEditProps = {
  categoria: {
    id: number;
    descricao: string;
  };
};

export type DialogDeleteProps = {
  categoria: {
    id: number;
    descricao: string;
  };
};

interface CategoriesResponse {
  status: number;
  code: string;
  message: string;
  data: Array<Category>;
}

export interface Category {
  id: number;
  descricao: string;
}

export const fetchCategories = async (): Promise<Array<Category>> => {
  const response = await api.get<CategoriesResponse>('/category');
  return response.data.data;
};

export async function insertCategoryService(data: CategoriaFormData) {
  const response = await api.post('/admin/category', data);
  return response.data.data;
}

type UpdateCategoryParams = {
  id: number;
  data: CategoriaFormData;
};

export async function updateCategoryService({
  id,
  data,
}: UpdateCategoryParams) {
  const response = await api.put(`/admin/category/${id}`, data);
  return response.data;
}

export async function deleteCategoryService(id: number) {
  const response = await api.delete(`/admin/category/${id}`);
  return response.data;
}
