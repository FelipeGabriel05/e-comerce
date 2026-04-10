package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.User;
import ecommerce.Database.Repositories.UsersRepository;
import java.sql.Connection;

public class LoginUseCase {

  public LoginUseCase() {}

  public User execute(String login, String senha) {
    try {
      Connection dbConnection = DBConnection.getConnection();
      UsersRepository usersRepository = new UsersRepository(dbConnection);
      return usersRepository.findUserByLoginAndSenha(login, senha);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public User findUserById(int id) {
    try {
      Connection dbConnection = DBConnection.getConnection();
      UsersRepository usersRepository = new UsersRepository(dbConnection);
      return usersRepository.findUserById(id);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
