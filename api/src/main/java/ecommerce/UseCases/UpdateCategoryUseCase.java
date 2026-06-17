package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Category;
import ecommerce.Database.Repositories.CategoriesRepository;
import java.sql.Connection;

public class UpdateCategoryUseCase {

  public UpdateCategoryUseCase() {}

  public boolean execute(Category category) throws Exception {

    try {

      Connection dbConnection = DBConnection.getConnection();

      CategoriesRepository categoriesRepository = new CategoriesRepository(dbConnection);

      return categoriesRepository.updateCategory(category);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
