package ecommerce.Http.IO.Requests;

import java.util.List;

public class CheckoutBodyRequest {

  public List<CheckoutProductItemRequest> products;

  public static class CheckoutProductItemRequest {

    public int productId;
    public int quantity;
  }
}
