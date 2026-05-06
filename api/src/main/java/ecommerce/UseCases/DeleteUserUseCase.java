package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Repositories.UsersRepository;
import ecommerce.Exceptions.ValidationException;
import java.sql.Connection;

public class DeleteUserUseCase {

  public void execute(int id) throws ValidationException {

    try {
      Connection con = DBConnection.getConnection();
      UserRepository userRepository = new UserRepository(con);
      boolean deleted = userRepository.deleteUserById(id);

      if (!deleted) {
        throw new ValidationException("User not found or could not be deleted");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new ValidationException("Internal error while deleting user");
    }
  }
}
