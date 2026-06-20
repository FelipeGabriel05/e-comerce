package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import java.sql.Connection;

public class UpdateProductUseCase {

  public Product execute(Product product, String baseUrl)
      throws NotFoundException, InternalServerException, Exception {

    try {

      Connection con = DBConnection.getConnection();

      ProductRepository repository = new ProductRepository(con);

      if (product.getFoto() != null && product.getFoto().startsWith(baseUrl)) {
        String filename = product.getFoto().substring(product.getFoto().lastIndexOf('/') + 1);
        product.setFoto(filename);
      }

      if (product.getFoto() != null && product.getFoto().contains(";base64,")) {
        UploadImageUseCase imageUseCase = new UploadImageUseCase();
        String savedFilename = imageUseCase.execute(product.getFoto());
        product.setFoto(savedFilename);
      }

      Product updatedProduct = repository.updateProduct(product);

      if (updatedProduct == null) {
        throw new NotFoundException("Product not found or could not be updated");
      }

      return updatedProduct;

    } catch (NotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
  }
}
