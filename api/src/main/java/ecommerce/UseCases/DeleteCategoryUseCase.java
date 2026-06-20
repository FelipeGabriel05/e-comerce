package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Repositories.CategoriesRepository;
import java.sql.Connection;

public class DeleteCategoryUseCase {

  public DeleteCategoryUseCase() {}

  public boolean execute(int id) throws Exception {

    try {

      Connection dbConnection = DBConnection.getConnection();

      CategoriesRepository categoriesRepository = new CategoriesRepository(dbConnection);

      return categoriesRepository.deleteCategory(id);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
