package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.User;
import ecommerce.Database.Repositories.UsersRepository;
import ecommerce.Exceptions.DuplicateUserException;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateUserUseCase {

  public CreateUserUseCase() {}

  public User execute(User user) throws DuplicateUserException, SQLException {

    Connection dbConnection = DBConnection.getConnection();

    UsersRepository usersRepository = new UsersRepository(dbConnection);

    return usersRepository.createUser(user);
  }
}
