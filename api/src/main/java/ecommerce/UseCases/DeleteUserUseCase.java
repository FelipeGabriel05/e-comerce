package ecommerce.UseCases;

import ecommerce.Database.Repositories.UserRepository;
import ecommerce.Exceptions.ValidationException;

public class deleteUserUseCase {

  private UserRepository userRepository = new UserRepository();

  public void execute(int id) throws ValidationException {

    boolean deleted = userRepository.deleteUserById(id);

    if (!deleted) {
      throw new ValidationException("User not found or could not be deleted");
    }
  }
}
