package ecommerce.Database.Repositories;

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

  public Sale createSale(Sale sale) {
    RuntimeException failure = null;

    try {
      con.setAutoCommit(false);
      insertSale(sale);
      insertSaleItems(sale);
      con.commit();
      return sale;
    } catch (RuntimeException e) {
      failure = e;
      try {
        con.rollback();
      } catch (SQLException rollbackEx) {
        failure.addSuppressed(rollbackEx);
      }
      throw failure;
    } catch (SQLException e) {
      failure = new RuntimeException("Failed to create sale", e);
      try {
        con.rollback();
      } catch (SQLException rollbackEx) {
        failure.addSuppressed(rollbackEx);
      }
      throw failure;
    } finally {
      try {
        con.setAutoCommit(true);
      } catch (SQLException e) {
        if (failure != null) {
          failure.addSuppressed(e);
          throw failure;
        } else {
          throw new RuntimeException("Failed to restore autoCommit", e);
        }
      }
    }
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

  public List<Sale> findAllSales() {

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
      throw new RuntimeException(e);
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

        items.add(item);
      }

      return items;

    } catch (SQLException e) {
      throw e;
    }
  }
}
