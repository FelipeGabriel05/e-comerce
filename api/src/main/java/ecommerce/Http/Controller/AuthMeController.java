package ecommerce.Http.Controller;

import ecommerce.Database.Entites.User;
import ecommerce.Http.IO.Responses.JsonResponse;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/me")
public class AuthMeController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public AuthMeController() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json");

    try {
      User user = (User) request.getAttribute("user");
      user.setSenha(null);
      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_OK, "User info", user);
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
