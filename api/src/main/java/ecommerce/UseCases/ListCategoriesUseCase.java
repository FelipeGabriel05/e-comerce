package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Category;
import ecommerce.Database.Repositories.CategoriesRepository;
import java.sql.Connection;
import java.util.List;

public class ListCategoriesUseCase {

  public ListCategoriesUseCase() {}

  public List<Category> execute() throws Exception {
    try {
      Connection dbConnection = DBConnection.getConnection();
      CategoriesRepository categoriesRepository = new CategoriesRepository(dbConnection);

      return categoriesRepository.listCategories();

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
