package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Repositories.SaleRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListCustomerSalesUseCase {

  public ListCustomerSalesUseCase() {}

  public List<Sale> execute(int userId) throws SQLException {

    Connection dbConnection = DBConnection.getConnection();
    SaleRepository saleRepository = new SaleRepository(dbConnection);
    return saleRepository.findSalesByUserId(userId);

  }
}
