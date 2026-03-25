package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.User;
import ecommerce.Database.Repositories.UsersRepository;
import java.sql.Connection;

public class CreateUserUseCase {

  public CreateUserUseCase() {}

  public User execute(User user) {
    try {
      Connection dbConnection = DBConnection.getConnection();
      UsersRepository usersRepository = new UsersRepository(dbConnection);
      return usersRepository.createUser(user);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
