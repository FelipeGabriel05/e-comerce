package ecommerce.Http.Controllers;

import ecommerce.Http.Validators.HttpUserValidators;
import ecommerce.UseCases.DeleteUserUseCase;
import ecommerce.Exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserController {

  private HttpUserValidators validator = new HttpUserValidators();
  private DeleteUserUseCase useCase = new DeleteUserUseCase();

  public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {

    try {
      int id = validator.validateDeleteUser(request);

      useCase.execute(id);

      response.getWriter().write("User deleted successfully");

    } catch (ValidationException e) {
      response.setStatus(400);
      response.getWriter().write(e.getMessage());
    } catch (Exception e) {
      response.setStatus(500);
      response.getWriter().write("Internal server error");
    }
  }
}
