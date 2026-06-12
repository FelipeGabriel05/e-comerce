package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Repositories.UsersRepository;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import java.sql.Connection;

public class DeleteUserUseCase {

  public void execute(int id) throws NotFoundException, InternalServerException {

    try {
      Connection con = DBConnection.getConnection();

      UsersRepository userRepository = new UsersRepository(con);

      boolean deleted = userRepository.deleteUserById(id);

      if (!deleted) {
        throw new NotFoundException("User not found");
      }

    } catch (NotFoundException e) {
      throw e;

    } catch (Exception e) {
      throw new InternalServerException("Internal error while deleting user");
    }
  }
}
