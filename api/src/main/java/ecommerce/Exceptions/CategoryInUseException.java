package ecommerce.Exceptions;

public class CategoryInUseException extends Exception {
  public CategoryInUseException(String message) {
    super(message);
  }
}
