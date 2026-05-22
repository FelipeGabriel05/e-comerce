package ecommerce.Database.Entites;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
  private List<CartItem> items = new ArrayList<>();
  private double total = 0.0;

  public Cart() {}

  public void addItem(Product product, int quantity) {
    if (quantity <= 0) return;

    for (CartItem item : items) {
      if (item.getProduct().getId() == product.getId()) {
        item.increaseQuantity(quantity);
        recalculateTotal();
        return;
      }
    }

    items.add(new CartItem(product, quantity));
    recalculateTotal();
  }

  public void updateItem(Product product, int quantity) {
    if (quantity <= 0) {
      removeItem(product.getId());
      return;
    }

    for (CartItem item : items) {
      if (item.getProduct().getId() == product.getId()) {
        item.setQuantity(quantity);
        recalculateTotal();
        return;
      }
    }

    items.add(new CartItem(product, quantity));
    recalculateTotal();
  }

  public void removeItem(int productId) {
    Iterator<CartItem> iterator = items.iterator();

    while (iterator.hasNext()) {
      CartItem item = iterator.next();
      if (item.getProduct().getId() == productId) {
        iterator.remove();
        break;
      }
    }

    recalculateTotal();
  }

  public void clear() {
    items.clear();
    total = 0.0;
  }

  private void recalculateTotal() {
    total = items.stream().mapToDouble(CartItem::getSubtotal).sum();
  }

  public List<CartItem> getItems() {
    return items;
  }

  public double getTotal() {
    return total;
  }
}

class CartItem {
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
