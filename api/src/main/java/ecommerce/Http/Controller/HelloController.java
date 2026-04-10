package ecommerce.Http.Controller;

import ecommerce.Http.IO.Responses.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class HelloController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public HelloController() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_OK, "Hello, World!");
    response.setStatus(jsonRes.getStatus());
    response.getWriter().write(jsonRes.toJson());
  }
}
