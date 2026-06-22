package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Exceptions.ValidationException;
import java.sql.Connection;
import java.util.List;

public class ListProductsUseCase {
  public ListProductsUseCase() {}

  public List<Product> execute(String baseUrl) throws ValidationException {
    return execute(baseUrl, true);
  }

  public List<Product> execute(String baseUrl, boolean onlyAvailable) throws ValidationException {
    try {
      Connection con = DBConnection.getConnection();
      ProductRepository repository = new ProductRepository(con);
      List<Product> products =
          onlyAvailable ? repository.listAvailableProducts() : repository.listAllProducts();
      for (Product p : products) {
        if (p.getFoto() != null && !p.getFoto().isEmpty() && !p.getFoto().startsWith("http")) {
          p.setFoto(baseUrl + "/image/" + p.getFoto());
        }
      }
      return products;

    } catch (Exception e) {
      e.printStackTrace();
      throw new ValidationException("Error while listing products");
    }
  }
}
