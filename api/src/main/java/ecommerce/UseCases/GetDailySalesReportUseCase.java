package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Sale.DailySalesReportDTO;
import ecommerce.Database.Repositories.SaleRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GetDailySalesReportUseCase {

    public GetDailySalesReportUseCase() {}

    public List<DailySalesReportDTO> execute(String startDate, String endDate) throws SQLException {

        Connection dbConnection = DBConnection.getConnection();
        SaleRepository saleRepository = new SaleRepository(dbConnection);

        if (startDate.length() == 10) startDate += " 00:00:00";
        if (endDate.length() == 10) endDate += " 23:59:59";

        return saleRepository.getDailySalesReport(startDate, endDate);
    }
}


