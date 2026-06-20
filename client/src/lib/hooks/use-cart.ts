import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import {
  addToCartService,
  type CartData,
  fetchCartService,
  removeFromCartService,
  updateCartService,
} from '@/lib/services/cart.service';

const CART_QUERY_KEY = ['cart'];

export const useCart = () => {
  const queryClient = useQueryClient();

  const { data: cart } = useQuery<CartData>({
    queryKey: CART_QUERY_KEY,
    queryFn: fetchCartService,
  });

  const addMutation = useMutation({
    mutationFn: addToCartService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: CART_QUERY_KEY });
    },
  });

  const updateMutation = useMutation({
    mutationFn: updateCartService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: CART_QUERY_KEY });
    },
  });

  const removeMutation = useMutation({
    mutationFn: removeFromCartService,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: CART_QUERY_KEY });
    },
  });

  return {
    cart,
    total: cart?.total ?? 0,
    addToCart: (productId: number, quantity = 1) =>
      addMutation.mutate({ productId, quantity }),
    updateCart: (productId: number, quantity: number) =>
      updateMutation.mutate({ productId, quantity }),
    removeFromCart: (productId: number) => removeMutation.mutate(productId),
  };
};
