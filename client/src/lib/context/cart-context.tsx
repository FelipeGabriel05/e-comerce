import { createContext, useContext, useState } from 'react';

interface CartContextType {
  total: number;
  addToCart: (productId: number, quantity?: number) => void;
}

const CartContext = createContext<CartContextType>({
  total: 0,
  addToCart: () => {},
});

export const CartProvider = ({ children }: { children: React.ReactNode }) => {
  const [total, setTotal] = useState(0);

  const addToCart = (_productId: number, quantity = 1) => {
    setTotal((prev) => prev + quantity);
  };

  return (
    <CartContext.Provider value={{ total, addToCart }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCartContext = () => useContext(CartContext);
