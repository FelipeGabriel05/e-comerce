import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import { addToCart, fetchCart, removeFromCart } from '@/lib/services/cart';

export const useCart = () => {
  const queryClient = useQueryClient();

  const { data: cart } = useQuery({
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
    cart?.items.reduce((sum, item) => sum + item.quantity, 0) ?? 0;

  return {
    cart,
    totalItems,
    addToCart: (productId: number, quantity = 1) =>
      addMutation.mutate({ productId, quantity }),
    removeFromCart: (productId: number) => removeMutation.mutate(productId),
  };
};
