package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Category;
import ecommerce.Database.Repositories.CategoriesRepository;
import java.sql.Connection;

public class CreateCategoryUseCase {

  public CreateCategoryUseCase() {}

  public Category execute(Category category) {

    try {

      Connection dbConnection = DBConnection.getConnection();

      CategoriesRepository categoriesRepository = new CategoriesRepository(dbConnection);

      return categoriesRepository.createCategory(category);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
