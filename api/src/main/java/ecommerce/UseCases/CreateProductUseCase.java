package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import java.sql.Connection;

public class CreateProductUseCase {

  public CreateProductUseCase() {}

  public Product execute(Product product) {
    try {
      Connection dbConnection = DBConnection.getConnection();
      ProductRepository productRepository = new ProductRepository(dbConnection);
      return productRepository.createProduct(product);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
