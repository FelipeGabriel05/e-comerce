package ecommerce.Http.Controller;

import com.google.gson.Gson;
import ecommerce.Database.Entites.Cart.Cart;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Entites.User;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.CreateSaleUseCase;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sales")
public class SaleController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final String CART_COOKIE_NAME = "cart";
  private static final int EMPTY_COOKIE_MAX_AGE = 0;

  private final Gson gson = new Gson();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");

    try {
      User user = (User) request.getAttribute("user");
      Cart cart = getCartFromCookie(request);

      if (cart == null || cart.getItems().isEmpty()) {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "Cart is empty");
        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
        return;
      }

      CreateSaleUseCase createSaleUseCase = new CreateSaleUseCase();
      Sale createdSale = createSaleUseCase.execute(user.getId(), cart);

      clearCartCookie(response);
      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_CREATED, "Sale created", createdSale);
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    } catch (Exception e) {
      e.printStackTrace();
      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
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

  private void clearCartCookie(HttpServletResponse response) {
    Cookie cookie = new Cookie(CART_COOKIE_NAME, "");
    cookie.setPath("/");
    cookie.setMaxAge(EMPTY_COOKIE_MAX_AGE);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
  }
}
