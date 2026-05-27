package ecommerce.Http.Controllers;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpUserValidators;
import ecommerce.UseCases.DeleteUserUseCase;
import ecommerce.UseCases.UpdateUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UserController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private HttpUserValidators validator = new HttpUserValidators();

  private DeleteUserUseCase deleteUseCase =
      new DeleteUserUseCase();

  private UpdateUseCase updateUseCase =
      new UpdateUseCase();

  @Override
  protected void doDelete(
      HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    try {
      int id = validator.validateDeleteUser(request);

      deleteUseCase.execute(id);

      JsonResponse.send(
          response,
          200,
          "User deleted successfully");

    } catch (ValidationException e) {

      JsonResponse.send(
          response,
          400,
          e.getMessage());

    } catch (Exception e) {
      e.printStackTrace();

      JsonResponse.send(
          response,
          500,
          "Internal server error");
    }
  }

  @Override
  protected void doPut(
      HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    try {

      User user = validator.validateUpdateUser(request);

      updateUseCase.execute(user);

      JsonResponse.send(
          response,
          200,
          "User updated successfully");

    } catch (ValidationException e) {

      JsonResponse.send(
          response,
          400,
          e.getMessage());

    } catch (NotFoundException e) {

      JsonResponse.send(
          response,
          404,
          e.getMessage());

    } catch (InternalServerException e) {

      e.printStackTrace();

      JsonResponse.send(
          response,
          500,
          e.getMessage());
    }
  }
}