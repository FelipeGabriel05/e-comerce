package ecommerce.Http.Controller;

import com.google.gson.Gson;
import ecommerce.Database.Entites.Cart.Cart;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Requests.CartItemBodyRequest;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpCartValidators;
import ecommerce.UseCases.AddItemToCartUseCase;
import ecommerce.UseCases.RemoveItemFromCartUseCase;
import ecommerce.UseCases.UpdateCartUseCase;
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
  private static final int ONE_MONTH_FOR_COOKIE_EXPIRATION = 30 * 24 * 60 * 60;

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

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    HttpCartValidators httpCartValidator = new HttpCartValidators();
    AddItemToCartUseCase addItemToCartUseCase = new AddItemToCartUseCase();

    try {
      CartItemBodyRequest body = httpCartValidator.validateAddItem(request);
      Cart rawCart = getCartFromCookie(request);
      Cart cart = addItemToCartUseCase.execute(rawCart, body);

      if (cart != null) {
        saveCartToCookie(response, cart);
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Item added to cart", cart);
        response.getWriter().write(jsonRes.toJson());
        return;
      }

      throw new Exception("Failed to add item to cart");
    } catch (ValidationException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response
          .getWriter()
          .write(new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()).toJson());
    } catch (Exception e) {
      handleError(response, e);
    }
  }

  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    HttpCartValidators httpCartValidator = new HttpCartValidators();
    UpdateCartUseCase updateCartUsecase = new UpdateCartUseCase();

    try {
      CartItemBodyRequest body = httpCartValidator.validateUpdateCart(request);
      Cart rawCart = getCartFromCookie(request);
      Cart cart = updateCartUsecase.execute(rawCart, body);

      if (cart != null) {
        saveCartToCookie(response, cart);
        JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_OK, "Cart updated", cart);
        response.getWriter().write(jsonRes.toJson());
        return;
      }
      throw new Exception("Failed to update cart");
    } catch (ValidationException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response
          .getWriter()
          .write(new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()).toJson());
    } catch (Exception e) {
      handleError(response, e);
    }
  }

  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    RemoveItemFromCartUseCase removeItemFromCartUseCase = new RemoveItemFromCartUseCase();

    try {
      String pathInfo = request.getPathInfo();
      if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return;
      }

      int productId = Integer.parseInt(pathInfo.substring(1));
      Cart rawCart = getCartFromCookie(request);
      Cart cart = removeItemFromCartUseCase.execute(rawCart, productId);

      if (cart != null) {
        saveCartToCookie(response, cart);
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Item removed from cart", cart);
        response.getWriter().write(jsonRes.toJson());
        return;
      }

      throw new Exception("Failed to remove item from cart");
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

  private void saveCartToCookie(HttpServletResponse response, Cart cart) {
    String json = gson.toJson(cart);
    String encoded = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    Cookie cookie = new Cookie(CART_COOKIE_NAME, encoded);
    cookie.setPath("/");
    cookie.setMaxAge(ONE_MONTH_FOR_COOKIE_EXPIRATION);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
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
