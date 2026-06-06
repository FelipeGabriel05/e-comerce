package ecommerce.Http.Controller;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.PathVariableExtractor;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpUserValidators;
import ecommerce.UseCases.DeleteUserUseCase;
import ecommerce.UseCases.UpdateUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/*")
public class UserController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private HttpUserValidators validator = new HttpUserValidators();
  private DeleteUserUseCase deleteUseCase = new DeleteUserUseCase();
  private UpdateUseCase updateUseCase = new UpdateUseCase();

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    try {
      int id = PathVariableExtractor.extractIntPathVariable(request, "/users/:id", "id");
      User user = validator.validateUpdateUser(request, id);
      updateUseCase.execute(user);
      user.setSenha(null);
      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "User Updated sucessfully", user);
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    } catch (ValidationException e) {
      JsonResponse jsonRes = new JsonResponse(422, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    } catch (NotFoundException e) {
      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    } catch (Exception e) {
      e.printStackTrace();
      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    try {
      int id = PathVariableExtractor.extractIntPathVariable(request, "/users/:id", "id");
      deleteUseCase.execute(id);
      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "User Deleted sucessfully", id);
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    } catch (ValidationException e) {
      JsonResponse jsonRes = new JsonResponse(422, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    } catch (Exception e) {
      e.printStackTrace();
      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }
}
