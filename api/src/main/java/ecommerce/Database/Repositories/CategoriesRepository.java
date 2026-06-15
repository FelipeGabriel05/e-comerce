package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.Category;
import ecommerce.Database.Queries.CategoriesQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriesRepository {

  private Connection con;

  public CategoriesRepository(Connection dbConnection) {
    con = dbConnection;
  }

  public Category createCategory(Category categoryInput) throws Exception {
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
      throw e;
    }
  }

  public List<Category> listCategories() throws Exception {

    List<Category> categories = new ArrayList<>();
    String query = CategoriesQueries.listCategoriesQuery;

    try (PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setDescricao(rs.getString("descricao"));
        categories.add(category);
      }

      return categories;

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
