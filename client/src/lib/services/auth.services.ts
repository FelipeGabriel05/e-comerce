import type { RegisterFormDataEdit } from '../pages/clients/schema-dados-pessoais';
import type { LoginFormData } from '../pages/login/schema';
import type { RegisterFormData } from '../pages/register/schema';
import { api } from './constants';

export async function getMeService() {
  const response = await api.get('/me');
  return response.data.data;
}

export async function loginService(data: LoginFormData) {
  const response = await api.post('/login', data);
  return response.data;
}

export async function registerService(data: RegisterFormData) {
  const response = await api.post('/register', data);
  return response.data;
}

export async function updateUserService(
  id: number,
  data: RegisterFormDataEdit,
) {
  console.log(data);
  const response = await api.put(`/users/${id}`, data);
  return response.data;
}

export async function deleteUserService(id: number) {
  const response = await api.delete(`/users/${id}`);
  return response.data;
}
