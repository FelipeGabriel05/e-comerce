package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Repositories.SaleRepository;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import java.sql.Connection;

public class DeleteSaleUseCase {

  public void execute(int saleId) throws NotFoundException, InternalServerException, Exception {

    try {
      Connection con = DBConnection.getConnection();

      SaleRepository repository = new SaleRepository(con);

      boolean deleted = repository.deleteSaleById(saleId);

      if (!deleted) {
        throw new NotFoundException("Sale not found");
      }

    } catch (NotFoundException e) {
      throw e;

    } catch (Exception e) {
      throw e;
    }
  }
}
