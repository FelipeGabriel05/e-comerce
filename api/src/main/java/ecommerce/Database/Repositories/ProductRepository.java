package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.Product;
import ecommerce.Database.Queries.ProductQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

  private Connection con;

  public ProductRepository(Connection dbConnection) {
    con = dbConnection;
  }

  // =========================
  // CREATE PRODUCT
  // =========================
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

  // =========================
  // LIST AVAILABLE PRODUCTS (STOCK > 0)
  // =========================
  public List<Product> listAvailableProducts() {
    try {
      String query = ProductQueries.listAvailableProductsQuery;

      PreparedStatement ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();

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

}
