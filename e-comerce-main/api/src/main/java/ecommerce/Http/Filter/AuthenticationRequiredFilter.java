package ecommerce.Http.Filter;

import ecommerce.Database.Entites.User;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.LoginUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthenticationRequiredFilter implements Filter {

  private record Route(String method, String path) {}

  private static final List<Route> protectedRoutes =
      List.of(
          new Route("POST", "/image/upload"),
          new Route("POST", "/admin/category"),
          new Route("POST", "/admin/products"),
          new Route("DELETE", "/admin/category"),
          new Route("DELETE", "/admin/products"),
          new Route("PUT", "/admin/category"),
          new Route("PUT", "/admin/products"));

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String path = req.getRequestURI().substring(req.getContextPath().length());
    String method = req.getMethod();

    boolean isProtected =
        protectedRoutes.stream()
            .anyMatch(r -> r.method().equalsIgnoreCase(method) && r.path().equals(path));

    if (!isProtected) {
      chain.doFilter(request, response);
      return;
    }

    String sessionToken = extractSessionToken(req);

    if (sessionToken != null) {
      LoginUseCase loginUseCase = new LoginUseCase();
      User user = loginUseCase.findUserBySessionToken(sessionToken);

      if (user != null) {
        req.setAttribute("user", user);
        chain.doFilter(request, response);
        return;
      }
    }

    res.setContentType("application/json");
    JsonResponse jsonRes =
        new JsonResponse(HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
    res.setStatus(jsonRes.getStatus());
    res.getWriter().write(jsonRes.toJson());
  }

  private String extractSessionToken(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if (cookies == null) return null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("session_token")) return cookie.getValue();
    }
    return null;
  }

  @Override
  public void destroy() {}
}
