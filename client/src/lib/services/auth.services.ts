import type { AuthUser } from '../hooks/use-auth';
import type { LoginFormData } from '../pages/login/schema';
import type { RegisterFormData } from '../pages/register/schema';
import { api } from './constants';

export async function getMeService(): Promise<AuthUser | null> {
  try {
    const response = await api.get('/me');
    return response.data.data as AuthUser;
  } catch {
    return null;
  }
}

export async function loginService(data: LoginFormData) {
  const response = await api.post('/login', data);
  return response.data; // { data: { ...AuthUser } }
}

export async function logoutService(): Promise<void> {
  await api.post('/logout');
}

export async function registerService(data: RegisterFormData) {
  const response = await api.post('/register', data);
  return response.data;
}
