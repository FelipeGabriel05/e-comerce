package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.Category;
import ecommerce.Database.Queries.CategoriesQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriesRepository {

  private Connection con;

  public CategoriesRepository(Connection dbConnection) {
    con = dbConnection;
  }

  public Category createCategory(Category categoryInput) {
    try {

      String query = CategoriesQueries.insertCategoryQuery;

      PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, categoryInput.getDescricao());

      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();

      Category category = new Category();

      if (rs.next()) {
        category.setId(rs.getInt(1));
      }

      category.setDescricao(categoryInput.getDescricao());

      return category;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
