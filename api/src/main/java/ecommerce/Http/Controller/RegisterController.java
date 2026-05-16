package ecommerce.Http.Controller;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.DuplicateUserException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpUserValidators;
import ecommerce.UseCases.CreateUserUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    HttpUserValidators validators = new HttpUserValidators();

    try {
      User userInput = validators.validateCreateUser(request);

      CreateUserUseCase useCase = new CreateUserUseCase();

      User createdUser = useCase.execute(userInput);

      if (createdUser != null) {
        createdUser.setSenha(null);
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_CREATED, "User created", createdUser);

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());

      } else {

        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create user");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }

    } catch (ValidationException e) {
      JsonResponse jsonRes = new JsonResponse(422, e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (DuplicateUserException e) {
      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_CONFLICT, e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (Exception e) {
      e.printStackTrace();
      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }
}
