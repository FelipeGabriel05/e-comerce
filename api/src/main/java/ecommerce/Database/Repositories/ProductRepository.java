package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.Product;
import ecommerce.Database.Queries.ProductQueries;
import ecommerce.Exceptions.ProductInUseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

  private static final String SQLSTATE_FOREIGN_KEY_VIOLATION = "23503";

  private Connection con;

  public ProductRepository(Connection dbConnection) {
    con = dbConnection;
  }

  public Product createProduct(Product productInput) {
    try {
      String query = ProductQueries.insertProductQuery;
      PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, productInput.getDescricao());
      ps.setDouble(2, productInput.getPreco());
      ps.setString(3, productInput.getFoto());
      ps.setInt(4, productInput.getQuantidade());
      ps.setInt(5, productInput.getCategoriaId());

      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();

      Product product = new Product();

      if (rs.next()) {
        product.setId(rs.getInt(1));
      }

      product.setDescricao(productInput.getDescricao());
      product.setPreco(productInput.getPreco());
      product.setFoto(productInput.getFoto());
      product.setQuantidade(productInput.getQuantidade());
      product.setCategoriaId(productInput.getCategoriaId());

      return product;

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Product> listAvailableProducts() {
    return listProducts(ProductQueries.listAvailableProductsQuery);
  }

  public List<Product> listAllProducts() {
    return listProducts(ProductQueries.listAllProductsQuery);
  }

  private List<Product> listProducts(String query) {
    try {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      List<Product> products = new ArrayList<>();

      while (rs.next()) {
        Product product = new Product();

        product.setId(rs.getInt("id"));
        product.setDescricao(rs.getString("descricao"));
        product.setPreco(rs.getDouble("preco"));
        product.setFoto(rs.getString("foto"));
        product.setQuantidade(rs.getInt("quantidade"));
        product.setCategoriaId(rs.getInt("categoria_id"));

        products.add(product);
      }

      return products;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public Product findById(int id) {
    try {
      String query = ProductQueries.selectProductByIdQuery;
      PreparedStatement ps = con.prepareStatement(query);
      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setDescricao(rs.getString("descricao"));
        product.setPreco(rs.getDouble("preco"));
        product.setFoto(rs.getString("foto"));
        product.setQuantidade(rs.getInt("quantidade"));
        product.setCategoriaId(rs.getInt("categoria_id"));
        return product;
      }

      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Product updateProduct(Product productInput) throws SQLException {

    String query = ProductQueries.updateProductQuery;

    PreparedStatement ps = con.prepareStatement(query);

    ps.setString(1, productInput.getDescricao());
    ps.setDouble(2, productInput.getPreco());
    ps.setString(3, productInput.getFoto());
    ps.setInt(4, productInput.getQuantidade());
    ps.setInt(5, productInput.getCategoriaId());
    ps.setInt(6, productInput.getId());

    int rowsAffected = ps.executeUpdate();

    if (rowsAffected == 0) {
      return null;
    }

    return productInput;
  }

  public boolean deleteProduct(int id) throws ProductInUseException, Exception {

    String query = ProductQueries.deleteProductQuery;

    try (PreparedStatement ps = con.prepareStatement(query)) {

      ps.setInt(1, id);

      int rowsAffected = ps.executeUpdate();

      return rowsAffected > 0;

    } catch (SQLException e) {
      if (SQLSTATE_FOREIGN_KEY_VIOLATION.equals(e.getSQLState())) {
        throw new ProductInUseException("Product is in use and cannot be deleted");
      }
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
