package ecommerce.Http.Validators;

import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.BodyJsonToObject;
import ecommerce.Http.IO.Requests.CartItemBodyRequest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpCartValidators {

  public CartItemBodyRequest validateAddItem(HttpServletRequest request)
      throws ValidationException {
    List<String> errors = new ArrayList<>();

    CartItemBodyRequest body = null;
    try {
      body = BodyJsonToObject.parse(request, CartItemBodyRequest.class);
    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
    }

    if (body.productId == null) {
      errors.add("Product ID is required");
    }

    if (body.quantity == null || body.quantity <= 0) {
      errors.add("Quantity must be greater than zero");
    }

    String errorMessage = String.join(", ", errors);
    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    return body;
  }
}
