package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Repositories.SaleRepository;
import java.sql.Connection;
import java.util.List;

public class ListCustomerSalesUseCase {

  public List<Sale> execute(int userId) {

    Connection dbConnection = new DBConnection.getConnection();

    SaleRepository repository = new SaleRepository(dbConnection);

    return repository.findSalesByUserId(userId);
  }
}
