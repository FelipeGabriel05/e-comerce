package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.User;
import ecommerce.Database.Repositories.UserRepository;
import ecommerce.Exceptions.ValidationException;
import java.sql.Connection;

public class UpdateUseCase {
  public void execute(User user) throws ValidationException {
    try {
      Connection con = DBConnection.getConnection();
      UserRepository userRepository = new UserRepository(con);
      boolean updated = userRepository.updateUser(user);

      if (!updated) {
        throw new ValidationException("User not found or could not be updated");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new ValidationException("Internal error while updating user");
    }
  }
}
