package ecommerce.Http.Controller;

import ecommerce.Database.Entites.User;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpLoginValidators;
import ecommerce.UseCases.LoginUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public LoginController() {
    super();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpLoginValidators validators = new HttpLoginValidators();
    response.setContentType("application/json");

    try {
      User credentials = validators.validateLogin(request);
      LoginUseCase loginUseCase = new LoginUseCase();
      User user = loginUseCase.execute(credentials.getLogin(), credentials.getSenha());

      response.setContentType("application/json");
      if (user != null) {
        int EXPIRATION_IN_ONE_DAY = 60 * 60 * 24;
        Cookie authCookie = new Cookie("user_id", String.valueOf(user.getId()));
        authCookie.setMaxAge(EXPIRATION_IN_ONE_DAY);
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        response.addCookie(authCookie);

        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Login successful", user);
        response.getWriter().write(jsonRes.toJson());
      } else {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }
    } catch (Exception e) {
      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }
}
