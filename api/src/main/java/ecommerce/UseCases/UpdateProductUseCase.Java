package ecommerce.UseCases;

import ecommerce.Database.Connection.ConnectionDatabase;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import java.sql.Connection;

public class UpdateProductUseCase {

  public Product execute(Product productInput) {

    try {

      Connection con =
          ConnectionDatabase.getConnection();

      ProductRepository repository =
          new ProductRepository(con);

      Product updatedProduct =
          repository.updateProduct(productInput);

      con.close();

      return updatedProduct;

    } catch (Exception e) {

      e.printStackTrace();
      return null;
    }
  }
}