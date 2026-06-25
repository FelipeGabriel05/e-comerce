package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Repositories.SaleRepository;
import java.sql.Connection;
import java.util.List;

public class ListCustomerSalesUseCase {

  public ListCustomerSalesUseCase() {}

  public List<Sale> execute(int userId) throws Exception {

    try {
      Connection dbConnection = DBConnection.getConnection();
      SaleRepository saleRepository = new SaleRepository(dbConnection);

      return saleRepository.findSalesByUserId(userId);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
