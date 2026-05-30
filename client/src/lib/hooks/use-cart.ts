import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

interface CartItem {
  productId: number;
  quantity: number;
  preco?: number;
  descricao?: string;
}

interface CartData {
  items: Array<CartItem>;
}

const fetchCart = async (): Promise<CartData> => {
  return { items: [] };
};

const addToCart = async (
  productId: number,
  quantity: number,
): Promise<void> => {
  console.log('Adicionando ao carrinho:', productId, quantity);
};

const removeFromCart = async (productId: number): Promise<void> => {
  console.log('Removendo do carrinho:', productId);
};

export const useCart = () => {
  const queryClient = useQueryClient();

  const { data: cart } = useQuery<CartData>({
    queryKey: ['cart'],
    queryFn: fetchCart,
  });

  const addMutation = useMutation({
    mutationFn: ({
      productId,
      quantity,
    }: {
      productId: number;
      quantity: number;
    }) => addToCart(productId, quantity),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['cart'] });
    },
  });

  const removeMutation = useMutation({
    mutationFn: (productId: number) => removeFromCart(productId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['cart'] });
    },
  });

  const totalItems =
    cart?.items.reduce(
      (sum: number, item: CartItem) => sum + item.quantity,
      0,
    ) ?? 0;

  return {
    cart,
    totalItems,
    addToCart: (productId: number, quantity = 1) =>
      addMutation.mutate({ productId, quantity }),
    removeFromCart: (productId: number) => removeMutation.mutate(productId),
  };
};
