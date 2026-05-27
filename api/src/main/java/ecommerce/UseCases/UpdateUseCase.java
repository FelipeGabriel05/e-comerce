package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.User;
import ecommerce.Database.Repositories.UsersRepository;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import java.sql.Connection;

public class UpdateUseCase {

  public void execute(User user)
      throws NotFoundException, InternalServerException {

    try {
      Connection con = DBConnection.getConnection();

      UsersRepository userRepository = new UsersRepository(con);

      boolean updated = userRepository.updateUser(user);

      if (!updated) {
        throw new NotFoundException(
            "User not found or could not be updated");
      }

    } catch (NotFoundException e) {
      throw e;

    } catch (Exception e) {
      throw new InternalServerException(
          "Internal error while updating user");
    }
  }
}
