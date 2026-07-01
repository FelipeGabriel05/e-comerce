package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Sale.OutOfStockProductReportDTO;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Exceptions.InternalServerException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListOutOfStockProductsUseCase {

  public List<OutOfStockProductReportDTO> execute() throws InternalServerException {

    try {
      Connection con = DBConnection.getConnection();

      ProductRepository repository = new ProductRepository(con);

      return repository.listOutOfStockProducts();

    } catch (SQLException e) {
      throw new InternalServerException(e.getMessage());
    }
  }
}
