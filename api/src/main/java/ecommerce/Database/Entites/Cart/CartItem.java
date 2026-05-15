package ecommerce.Database.Entites.Cart;

import ecommerce.Database.Entites.Product;

public class CartItem {
  private Product product;
  private int quantity;
  private double subtotal;

  public CartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    recalculateSubtotal();
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
    recalculateSubtotal();
  }

  public double getSubtotal() {
    return subtotal;
  }

  public void increaseQuantity(int amount) {
    this.quantity += amount;
    recalculateSubtotal();
  }

  private void recalculateSubtotal() {
    this.subtotal = product.getPreco() * quantity;
  }
}
