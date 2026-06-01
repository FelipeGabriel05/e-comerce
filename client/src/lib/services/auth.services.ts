import type { LoginFormData } from '../pages/login/schema';
import type { RegisterFormData } from '../pages/register/schema';
import type { AuthUser } from '../hooks/use-auth';
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
  return response.data;
}

export async function logoutService() {
  const response = await api.post('/logout');
  return response.data;
}

export async function registerService(data: RegisterFormData) {
  const response = await api.post('/register', data);
  return response.data;
}
