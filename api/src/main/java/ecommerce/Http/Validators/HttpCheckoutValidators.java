package ecommerce.Http.Validators;

import ecommerce.Database.Entites.Cart.Cart;
import ecommerce.Database.Entites.Cart.CartItem;
import ecommerce.Exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class HttpCheckoutValidators {

  public Cart validateCheckout(Cart cart) throws ValidationException {

    List<String> errors = new ArrayList<>();

    if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
      errors.add("Cart is empty");
    } else {
      for (CartItem item : cart.getItems()) {

        if (item.getProduct() == null || item.getProduct().getId() <= 0) {
          errors.add("Product ID is required");
        }

        if (item.getQuantity() <= 0) {
          errors.add("Quantity must be greater than zero");
        }
      }
    }

    if (!errors.isEmpty()) {
      String errorMessage = String.join(", ", errors);

      throw new ValidationException(errorMessage);
    }

    return cart;
  }
}
