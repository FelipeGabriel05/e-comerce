package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Product;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.PathVariableExtractor;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpProductValidators;
import ecommerce.UseCases.UpdateStockUseCase;
import ecommerce.UseCases.ValidateStockUseCase;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products/stock/*")
public class StockController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    HttpProductValidators validators = new HttpProductValidators();

    try {
      int id = PathVariableExtractor.extractIntPathVariable(request, "/products/stock/:id", "id");

      Product productInput = validators.validateStock(request, id);

      ValidateStockUseCase useCase = new ValidateStockUseCase();

      boolean available = useCase.execute(productInput.getId(), productInput.getQuantidade());

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Stock validated", available);

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (ValidationException e) {

      JsonResponse jsonRes = new JsonResponse(422, e.getMessage());

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

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    HttpProductValidators validators = new HttpProductValidators();

    try {
      int id = PathVariableExtractor.extractIntPathVariable(request, "/products/stock/:id", "id");

      Product productInput = validators.validateUpdateProductQuantity(request, id);

      UpdateStockUseCase useCase = new UpdateStockUseCase();

      Product updatedProduct = useCase.execute(productInput);

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Stock updated", updatedProduct);

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
