package ecommerce.Http.Controller;

import com.google.gson.Gson;
import ecommerce.Database.Entites.Cart;
import ecommerce.Http.IO.Responses.JsonResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final Gson gson = new Gson();

  private static final String CART_COOKIE_NAME = "cart";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");

    try {
      Cart cart = getCartFromCookie(request);
      if (cart != null) {
        JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_OK, "Cart retrieved", cart);
        response.getWriter().write(jsonRes.toJson());
        return;
      }
      throw new Exception("Failed to retrieve cart");

    } catch (Exception e) {
      handleError(response, e);
    }
  }

  private Cart getCartFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (CART_COOKIE_NAME.equals(cookie.getName())) {
          try {
            String decoded =
                new String(Base64.getDecoder().decode(cookie.getValue()), StandardCharsets.UTF_8);
            return gson.fromJson(decoded, Cart.class);
          } catch (Exception e) {
            return new Cart();
          }
        }
      }
    }

    return new Cart();
  }

  private void handleError(HttpServletResponse response, Exception e) throws IOException {
    e.printStackTrace();
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    response
        .getWriter()
        .write(
            new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage())
                .toJson());
  }
}
