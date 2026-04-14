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
public class AdminRolePermissionRequiredFilter implements Filter {

  private record Route(String method, String path) {}

  private static final List<Route> adminRoutes =
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

    boolean isAdminRoute =
        adminRoutes.stream()
            .anyMatch(r -> r.method().equalsIgnoreCase(method) && r.path().equals(path));

    if (!isAdminRoute) {
      chain.doFilter(request, response);
      return;
    }

    User user = (User) req.getAttribute("user");

    if (user == null) {
      user = findUserFromCookie(req);
      if (user != null) {
        req.setAttribute("user", user);
      }
    }

    if (user != null && user.isAdministrador()) {
      chain.doFilter(request, response);
      return;
    }

    res.setContentType("application/json");
    JsonResponse jsonRes =
        new JsonResponse(HttpServletResponse.SC_FORBIDDEN, "Admin permission required");
    res.setStatus(jsonRes.getStatus());
    res.getWriter().write(jsonRes.toJson());
  }

  private User findUserFromCookie(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("user_id")) {
          try {
            int id = Integer.parseInt(cookie.getValue());
            LoginUseCase loginUseCase = new LoginUseCase();
            return loginUseCase.findUserById(id);
          } catch (NumberFormatException e) {
            return null;
          }
        }
      }
    }
    return null;
  }

  @Override
  public void destroy() {}
}
