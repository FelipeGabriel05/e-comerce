package ecommerce.Http.Validators;

import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.BodyJsonToObject;
import ecommerce.Http.IO.Requests.CheckoutBodyRequest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpCheckoutValidators {

  public CheckoutBodyRequest validateCheckout(HttpServletRequest request)
      throws ValidationException {

    List<String> errors = new ArrayList<>();

    CheckoutBodyRequest body = null;

    try {
      body = BodyJsonToObject.parse(request, CheckoutBodyRequest.class);

    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
    }

    if (body.products == null || body.products.isEmpty()) {
      errors.add("Products are required");
    } else {
      for (CheckoutBodyRequest.CheckoutProductItemRequest item : body.products) {

        if (item.productId <= 0) {
          errors.add("Product ID is required");
        }

        if (item.quantity <= 0) {
          errors.add("Quantity must be greater than zero");
        }
      }
    }

    if (!errors.isEmpty()) {
      String errorMessage = String.join(", ", errors);

      throw new ValidationException(errorMessage);
    }

    return body;
  }
}
