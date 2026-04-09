package ecommerce.Http.Controller;

import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpLoginValidators;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
    } catch (Exception e) {
      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }
}
