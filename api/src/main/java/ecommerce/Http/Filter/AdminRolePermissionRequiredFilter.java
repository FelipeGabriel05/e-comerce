package ecommerce.Http.Filter;

import ecommerce.Database.Entites.User;
import ecommerce.Http.IO.Responses.JsonResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminRolePermissionRequiredFilter implements Filter {

  private record Route(String method, String path) {}

  private static final List<Route> adminRoutes =
      List.of(
          new Route("POST", "/image/upload"),
          new Route("POST", "/admin/category"),
          new Route("POST", "/admin/products"),
          new Route("GET", "/admin/products/*"),
          new Route("DELETE", "/admin/category/*"),
          new Route("DELETE", "/admin/products/*"),
          new Route("PUT", "/admin/category/*"),
          new Route("PUT", "/admin/products/*"),
          new Route("GET", "/admin/sales"));

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
            .anyMatch(r -> r.method().equalsIgnoreCase(method) && matchesPath(r.path(), path));

    if (!isAdminRoute) {
      chain.doFilter(request, response);
      return;
    }

    User user = (User) req.getAttribute("user");

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

  private boolean matchesPath(String pattern, String path) {
    String[] patternParts = pattern.split("/");
    String[] pathParts = path.split("/");

    if (patternParts.length != pathParts.length) return false;

    for (int i = 0; i < patternParts.length; i++) {
      if (patternParts[i].equals("*")) continue;
      if (!patternParts[i].equals(pathParts[i])) return false;
    }
    return true;
  }

  @Override
  public void destroy() {}
}
