package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Sale.CustomerReportDTO;
import ecommerce.Database.Repositories.SaleRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GetSalesByCustomerReportUseCase {

  public GetSalesByCustomerReportUseCase() {}

  public List<CustomerReportDTO> execute(String startDate, String endDate) throws SQLException {

    Connection dbConnection = DBConnection.getConnection();
    SaleRepository saleRepository = new SaleRepository(dbConnection);

    if (startDate.length() == 10) startDate += " 00:00:00";
    if (endDate.length() == 10) endDate += " 23:59:59.999999";

    return saleRepository.getSalesByCustomerReport(startDate, endDate);
  }
}
