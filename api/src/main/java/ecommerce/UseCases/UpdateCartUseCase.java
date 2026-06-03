package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Cart.Cart;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Http.IO.Requests.CartItemBodyRequest;
import java.sql.Connection;

public class UpdateCartUseCase {

  public Cart execute(Cart cart, CartItemBodyRequest body) {
    try (Connection conn = DBConnection.getConnection()) {
      ProductRepository repository = new ProductRepository(conn);
      Product product = repository.findById(body.productId);

      if (body.quantity == 0) {
        cart.removeItem(body.productId);
      } else {
        cart.updateItem(product, body.quantity);
      }

      return cart;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
