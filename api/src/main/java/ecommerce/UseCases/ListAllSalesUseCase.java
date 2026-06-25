package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Repositories.SaleRepository;
import java.sql.Connection;
import java.util.List;

public class ListAllSalesUseCase {

  public ListAllSalesUseCase() {}

  public List<Sale> execute() throws Exception {

    try {
      Connection dbConnection = DBConnection.getConnection();
      SaleRepository saleRepository = new SaleRepository(dbConnection);

      return saleRepository.findAllSales();

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
