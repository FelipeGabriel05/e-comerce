import { api } from './constants';

export async function checkoutService(): Promise<void> {
  await api.post('/checkout');
}
