package ecommerce.Http.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecommerce.Database.Entites.User;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpLoginValidators;
import ecommerce.UseCases.LoginUseCase;

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
      LoginUseCase.LoginResult result =
          loginUseCase.execute(credentials.getLogin(), credentials.getSenha());

      if (result != null) {
        String cookieHeader =
            String.format(
                "session_token=%s; Path=/; Max-Age=%d; HttpOnly; SameSite=None; Secure",
                result.token(), LoginUseCase.ONE_DAY);
        response.addHeader("Set-Cookie", cookieHeader);

        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Login successful", result.user());
        response.getWriter().write(jsonRes.toJson());
      } else {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }
    } catch (Exception e) {
      e.printStackTrace();
      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }
}
