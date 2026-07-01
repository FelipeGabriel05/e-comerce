package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.Product;
import ecommerce.Database.Entites.Sale.CustomerReportDTO;
import ecommerce.Database.Entites.Sale.DailySalesReportDTO;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Entites.Sale.SaleItem;
import ecommerce.Database.Queries.SaleQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository {

  private final Connection con;

  public SaleRepository(Connection dbConnection) {
    this.con = dbConnection;
  }

  public Sale createSale(Sale sale) throws SQLException {
    insertSale(sale);
    insertSaleItems(sale);
    return sale;
  }

  private void insertSale(Sale saleInput) {
    try (PreparedStatement stmt =
        con.prepareStatement(SaleQueries.insertSaleQuery, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setInt(1, saleInput.getUserId());
      stmt.executeUpdate();
      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          saleInput.setId(generatedKeys.getInt(1));
        } else {
          throw new RuntimeException("Creating sale failed: no generated key returned");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to insert sale", e);
    }
  }

  private void insertSaleItems(Sale sale) throws SQLException {
    try (PreparedStatement stmt = con.prepareStatement(SaleQueries.insertSaleItemQuery)) {
      for (SaleItem item : sale.getItems()) {
        stmt.setInt(1, sale.getId());
        stmt.setInt(2, item.getProductId());
        stmt.setDouble(3, item.getPrice());
        stmt.setInt(4, item.getQuantity());
        stmt.addBatch();
      }
      stmt.executeBatch();
    }
  }

  public boolean deleteSaleById(int saleId) throws SQLException {

    try (PreparedStatement deleteItemsStmt =
            con.prepareStatement(SaleQueries.deleteSaleItemsBySaleIdQuery);
        PreparedStatement deleteSaleStmt = con.prepareStatement(SaleQueries.deleteSaleByIdQuery)) {

      deleteItemsStmt.setInt(1, saleId);
      deleteItemsStmt.executeUpdate();

      deleteSaleStmt.setInt(1, saleId);

      int rowsAffected = deleteSaleStmt.executeUpdate();

      return rowsAffected > 0;
    }
  }

  public List<Sale> findSalesByUserId(int userId) {

    List<Sale> sales = new ArrayList<>();

    try {
      PreparedStatement ps = con.prepareStatement(SaleQueries.selectSalesByUserIdQuery);

      ps.setInt(1, userId);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Sale sale = new Sale();

        sale.setId(rs.getInt("id"));
        sale.setDataHora(rs.getTimestamp("data_hora").toString());
        sale.setUserId(rs.getInt("usuario_id"));

        List<SaleItem> items = findItemsBySaleId(sale.getId());
        sale.setItems(items);

        double total =
            items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        sale.setTotal(total);

        sales.add(sale);
      }

      return sales;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Sale> findAllSales() throws SQLException {

    List<Sale> sales = new ArrayList<>();

    try {
      PreparedStatement ps = con.prepareStatement(SaleQueries.selectAllSalesQuery);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Sale sale = new Sale();

        sale.setId(rs.getInt("id"));
        sale.setDataHora(rs.getTimestamp("data_hora").toString());
        sale.setUserId(rs.getInt("usuario_id"));

        List<SaleItem> items = findItemsBySaleId(sale.getId());
        sale.setItems(items);

        double total =
            items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        sale.setTotal(total);

        sales.add(sale);
      }

      return sales;

    } catch (SQLException e) {
      throw e;
    }
  }

  private List<SaleItem> findItemsBySaleId(int saleId) throws SQLException {

    List<SaleItem> items = new ArrayList<>();

    try {
      PreparedStatement ps = con.prepareStatement(SaleQueries.selectSaleItemsBySaleIdQuery);

      ps.setInt(1, saleId);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        SaleItem item =
            new SaleItem(rs.getInt("produto_id"), rs.getDouble("preco"), rs.getInt("quantidade"));

        Product product = new Product();
        product.setId(rs.getInt("produto_id"));
        product.setDescricao(rs.getString("descricao"));
        product.setPreco(rs.getDouble("preco"));
        product.setFoto(rs.getString("foto"));
        product.setCategoriaId(rs.getInt("categoria_id"));

        item.setProduct(product);

        items.add(item);
      }

      return items;

    } catch (SQLException e) {
      throw e;
    }
  }

  public List<CustomerReportDTO> getSalesByCustomerReport(String startDate, String endDate)
      throws SQLException {

    List<CustomerReportDTO> report = new ArrayList<>();

    try (PreparedStatement ps =
        con.prepareStatement(SaleQueries.selectSalesByCustomerReportQuery)) {
      ps.setString(1, startDate);
      ps.setString(2, endDate);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {

          int clientId = rs.getInt("cliente_id");
          String clientName = rs.getString("cliente_nome");
          int totalPurchases = rs.getInt("qtde_compras");

          CustomerReportDTO itemReport =
              new CustomerReportDTO(clientId, clientName, totalPurchases);
          report.add(itemReport);
        }
      }

      return report;

    } catch (SQLException e) {
      throw e;
    }
  }

  public List<DailySalesReportDTO> getDailySalesReport(String startDate, String endDate)
      throws SQLException {

    List<DailySalesReportDTO> report = new ArrayList<>();

    try (PreparedStatement ps = con.prepareStatement(SaleQueries.selectDailySalesReportQuery)) {
      ps.setString(1, startDate);
      ps.setString(2, endDate);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {

          String date = rs.getDate("venda_data").toString();
          int totalOrders = rs.getInt("qtd_pedidos");
          double totalRevenue = rs.getDouble("faturamento_diario");

          DailySalesReportDTO itemReport = new DailySalesReportDTO(date, totalOrders, totalRevenue);
          report.add(itemReport);
        }
      }

      return report;

    } catch (SQLException e) {
      throw e;
    }
  }
}
