const API_BASE = 'http://localhost:8080';

export type CartItem = {
  product: {
    id: number;
    descricao: string;
    preco: number;
    foto: string;
    quantidade: number;
    categoriaId: number;
  };
  quantity: number;
  subtotal: number;
};

export type Cart = {
  items: Array<CartItem>;
  total: number;
};

export const fetchCart = async (): Promise<Cart> => {
  const response = await fetch(`${API_BASE}/cart`);
  if (!response.ok) throw new Error('Erro ao buscar carrinho');
  const data = await response.json();
  return data.data;
};

export const addToCart = async (
  productId: number,
  quantity: number,
): Promise<Cart> => {
  const response = await fetch(`${API_BASE}/cart`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ productId, quantity }),
  });
  if (!response.ok) throw new Error('Erro ao adicionar ao carrinho');
  const data = await response.json();
  return data.data;
};

export const removeFromCart = async (productId: number): Promise<Cart> => {
  const response = await fetch(`${API_BASE}/cart/${productId}`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error('Erro ao remover do carrinho');
  const data = await response.json();
  return data.data;
};
