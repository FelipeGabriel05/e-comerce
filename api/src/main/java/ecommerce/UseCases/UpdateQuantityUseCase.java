package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import java.sql.Connection;

public class UpdateQuantityUseCase {

  public Product execute(Product productInput) throws NotFoundException, InternalServerException {

    try (Connection con = DBConnection.getConnection()) {
      ProductRepository repository = new ProductRepository(con);
      Product updatedProduct = repository.updateProductQuantity(productInput);

      if (updatedProduct == null) {
        throw new NotFoundException("Product not found");
      }
      return updatedProduct;

    } catch (NotFoundException e) {
      throw e;

    } catch (Exception e) {
      throw new InternalServerException("Internal error while updating product quantity");
    }
  }
}
