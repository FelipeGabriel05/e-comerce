package ecommerce.Http.Controller;

import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.LogoutUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    try {

      String sessionToken = extractSessionToken(request);

      if (sessionToken == null) {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_UNAUTHORIZED, "User is not authenticated");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());

        return;
      }

      LogoutUseCase logoutUseCase = new LogoutUseCase();

      logoutUseCase.execute(sessionToken);

      Cookie sessionCookie = new Cookie("session_token", "");
      sessionCookie.setMaxAge(0);
      sessionCookie.setPath("/");
      response.addCookie(sessionCookie);

      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_OK, "Logout successful");

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

  private String extractSessionToken(HttpServletRequest req) {

    Cookie[] cookies = req.getCookies();

    if (cookies == null) return null;

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("session_token")) {
        return cookie.getValue();
      }
    }

    return null;
  }
}
