package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Repositories.ProductRepository;
import java.sql.Connection;

public class DeleteProductUseCase {

  public DeleteProductUseCase() {}

  public boolean execute(int id) throws Exception {

    try {

      Connection dbConnection = DBConnection.getConnection();

      ProductRepository productRepository = new ProductRepository(dbConnection);

      return productRepository.deleteProduct(id);

    } catch (Exception e) {
      throw e;
    }
  }
}
