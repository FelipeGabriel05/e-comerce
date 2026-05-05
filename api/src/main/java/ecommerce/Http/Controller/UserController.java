package ecommerce.Http.Controllers;

import ecommerce.Http.Validators.HttpUserValidators;
import ecommerce.UseCases.DeleteUserUseCase;
import ecommerce.Exceptions.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete")
public class UserController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private HttpUserValidators validator = new HttpUserValidators();
  private DeleteUserUseCase useCase = new DeleteUserUseCase();

  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    try {
      int id = validator.validateDeleteUser(request);

      useCase.execute(id);

      response.setStatus(200);
      response.getWriter().write("{\"message\": \"User deleted successfully\"}");

    } catch (ValidationException e) {
      response.setStatus(400);
      response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
    } catch (Exception e) {
      e.printStackTrace();
      response.setStatus(500);
      response.getWriter().write("{\"error\": \"Internal server error\"}");
    }
  }
}
