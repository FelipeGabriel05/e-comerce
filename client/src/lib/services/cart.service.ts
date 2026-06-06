import { api } from './constants';

interface CartProduct {
  id: number;
  descricao: string;
  preco: number;
  foto: string;
  quantidade: number;
  categoriaId: number;
}

interface CartItem {
  product: CartProduct;
  quantity: number;
  subtotal: number;
}

export interface CartData {
  items: Array<CartItem>;
  total: number;
}

interface CartResponse {
  status: number;
  message: string;
  data: CartData;
}

export async function fetchCartService(): Promise<CartData> {
  const response = await api.get<CartResponse>('/cart');
  return response.data.data;
}

export async function addToCartService({
  productId,
  quantity,
}: {
  productId: number;
  quantity: number;
}): Promise<CartData> {
  const response = await api.post<CartResponse>('/cart', {
    productId,
    quantity,
  });
  return response.data.data;
}

export async function updateCartService({
  productId,
  quantity,
}: {
  productId: number;
  quantity: number;
}): Promise<CartData> {
  const response = await api.put<CartResponse>('/cart', {
    productId,
    quantity,
  });
  return response.data.data;
}

export async function removeFromCartService(
  productId: number,
): Promise<CartData> {
  const response = await api.delete<CartResponse>(`/cart/${productId}`);
  return response.data.data;
}
