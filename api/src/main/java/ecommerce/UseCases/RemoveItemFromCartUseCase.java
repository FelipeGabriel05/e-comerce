package ecommerce.UseCases;

import ecommerce.Database.Entites.Cart.Cart;

public class RemoveItemFromCartUseCase {

  public Cart execute(Cart cart, Integer productId) {
    try {
      cart.removeItem(productId);
      return cart;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
