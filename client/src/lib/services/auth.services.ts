import type { LoginFormData } from '../pages/login/schema';
import type { RegisterFormData } from '../pages/register/schema';
import { api } from './constants';

export async function loginService(data: LoginFormData) {
  const response = await api.post('/login', data);
  return response.data;
}

export async function registerService(data: RegisterFormData) {
  console.log(data);
  const response = await api.post('/register', data);
  return response.data;
}
