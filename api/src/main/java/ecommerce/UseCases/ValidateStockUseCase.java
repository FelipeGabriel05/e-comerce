package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Exceptions.InternalServerException;
import java.sql.Connection;

public class ValidateStockUseCase {

  public boolean execute(int productId, int requestedQuantity) throws InternalServerException {

    try {
      Connection con = DBConnection.getConnection();

      ProductRepository repository = new ProductRepository(con);

      Product product = repository.findById(productId);

      con.close();

      if (product == null) {
        return false;
      }

      return product.getQuantidade() >= requestedQuantity;

    } catch (Exception e) {
      throw new InternalServerException("Internal error while validating stock");
    }
  }
}
