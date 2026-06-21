package ecommerce.Http.Controller;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Requests.CheckoutBodyRequest;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpCheckoutValidators;
import ecommerce.UseCases.FinishPurchaseUseCase;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    HttpCheckoutValidators validators = new HttpCheckoutValidators();

    try {
      User user = (User) request.getAttribute("user");

      if (user == null) {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
        return;
      }

      CheckoutBodyRequest checkout = validators.validateCheckout(request);

      FinishPurchaseUseCase useCase = new FinishPurchaseUseCase();

      useCase.execute(user.getId(), checkout);

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
}
