package ecommerce.Exceptions;

public class ProductInUseException extends Exception {
  public ProductInUseException(String message) {
    super(message);
  }
}
