package ecommerce.Database.Entites.Cart;

import ecommerce.Database.Entites.Product;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
  private static final int FREE_PRODUCT_MAX_QUANTITY = 1;

  private List<CartItem> items = new ArrayList<>();
  private double total = 0.0;

  public Cart() {}

  public void addItem(Product product, int quantity) {
    if (quantity <= 0) return;

    boolean free = isFree(product);

    for (CartItem item : items) {
      if (item.getProduct().getId() == product.getId()) {
        if (free) {
          item.setQuantity(FREE_PRODUCT_MAX_QUANTITY);
        } else {
          item.increaseQuantity(quantity);
        }
        recalculateTotal();
        return;
      }
    }

    items.add(new CartItem(product, free ? FREE_PRODUCT_MAX_QUANTITY : quantity));
    recalculateTotal();
  }

  public void updateItem(Product product, int quantity) {
    if (quantity <= 0) {
      removeItem(product.getId());
      return;
    }

    if (isFree(product)) {
      quantity = FREE_PRODUCT_MAX_QUANTITY;
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

  private boolean isFree(Product product) {
    return product.getPreco() <= 0;
  }
}
