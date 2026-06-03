package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Exceptions.ValidationException;
import java.sql.Connection;
import java.util.List;

public class ListProductsUseCase {
  public ListProductsUseCase() {}

  public List<Product> execute() throws ValidationException {
    try {
      Connection con = DBConnection.getConnection();
      ProductRepository repository = new ProductRepository(con);
      return repository.listAvailableProducts();

    } catch (Exception e) {
      e.printStackTrace();
      throw new ValidationException("Error while listing products");
    }
  }
}
