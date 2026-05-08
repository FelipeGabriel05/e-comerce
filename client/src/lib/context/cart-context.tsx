import { createContext, type ReactNode, useContext, useState } from 'react';

import type { Product } from '@/lib/types/product';

export type CartItem = Product & { cartQuantity: number };

type CartContextType = {
  items: Array<CartItem>;
  addToCart: (product: Product) => void;
  totalItems: number;
};

const CartContext = createContext<CartContextType | null>(null);

export const CartProvider = ({ children }: { children: ReactNode }) => {
  const [items, setItems] = useState<Array<CartItem>>([]);

  const addToCart = (product: Product) => {
    setItems((prev) => {
      const existing = prev.find((i) => i.id === product.id);
      if (existing) {
        return prev.map((i) =>
          i.id === product.id ? { ...i, cartQuantity: i.cartQuantity + 1 } : i,
        );
      }
      return [...prev, { ...product, cartQuantity: 1 }];
    });
  };

  const totalItems = items.reduce((sum, i) => sum + i.cartQuantity, 0);

  return (
    <CartContext.Provider value={{ items, addToCart, totalItems }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const ctx = useContext(CartContext);
  if (!ctx) throw new Error('useCart must be used within CartProvider');
  return ctx;
};
