package ecommerce.UseCases;

import ecommerce.Database.Entites.User;
import ecommerce.Database.Repositories.UserRepository;
import ecommerce.Exceptions.ValidationException;

public class UpdateUserUseCase {

  private UserRepository userRepository = new UserRepository();

  public void execute(User user) throws ValidationException {

    boolean updated = userRepository.updateUser(user);

    if (!updated) {
      throw new ValidationException("User not found or could not be updated");
    }
  }
}
