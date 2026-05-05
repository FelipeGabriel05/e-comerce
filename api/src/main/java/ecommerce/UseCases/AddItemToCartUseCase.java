package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Cart;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Http.IO.Requests.CartItemBodyRequest;
import java.sql.Connection;

public class AddItemToCartUseCase {

  public Cart execute(Cart cart, CartItemBodyRequest body) {
    try (Connection conn = DBConnection.getConnection()) {
      ProductRepository repository = new ProductRepository(conn);
      Product product = repository.findById(body.productId);

      if (product != null) {
        cart.addItem(product, body.quantity);
      }

      return cart;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
