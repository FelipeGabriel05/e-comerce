package ecommerce.Http.Controller;

import com.google.gson.Gson;
import ecommerce.Database.Entites.Cart.Cart;
import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpCheckoutValidators;
import ecommerce.UseCases.FinishPurchaseUseCase;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

  private final Gson gson = new Gson();

  private static final String CART_COOKIE_NAME = "cart";
  private static final int EMPTY_COOKIE_MAX_AGE = 0;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    try {
      User user = (User) request.getAttribute("user");

      Cart cart = getCartFromCookie(request);

      HttpCheckoutValidators validators = new HttpCheckoutValidators();

      Cart validatedCart = validators.validateCheckout(cart);

      FinishPurchaseUseCase useCase = new FinishPurchaseUseCase();

      useCase.execute(user.getId(), validatedCart);

      clearCartCookie(response);

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Purchase finished successfully");

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (ValidationException e) {

      JsonResponse jsonRes = new JsonResponse(422, e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (NotFoundException e) {

      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage());

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
