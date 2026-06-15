import type { AuthUser } from '../hooks/use-auth';
import type { RegisterFormDataEdit } from '../pages/clients/schema-dados-pessoais';
import type { LoginFormData } from '../pages/login/schema';
import type { RegisterFormData } from '../pages/register/schema';
import { api } from './constants';

export async function getMeService(): Promise<AuthUser | null> {
  try {
    const { data } = await api.get('/me');
    return data.data;
  } catch {
    return null;
  }
}

export async function logoutService(): Promise<void> {
  try {
    await api.post('/logout');
  } catch {
    throw new Error('Falha no logout');
  }
}

export async function loginService(data: LoginFormData) {
  const response = await api.post('/login', data);
  return response.data; // { data: { ...AuthUser } }
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
