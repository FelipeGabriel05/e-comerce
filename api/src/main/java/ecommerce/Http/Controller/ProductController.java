package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Product;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.PathVariableExtractor;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.ListProductsUseCase;
import ecommerce.UseCases.UpdateProductUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/products/*")
@MultipartConfig
public class ProductController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    try {
      ListProductsUseCase useCase = new ListProductsUseCase();

      List<Product> products = useCase.execute();

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Products listed", products);

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (Exception e) {
      e.printStackTrace();

      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    HttpProductValidators validators = new HttpProductValidators();

    response.setContentType("application/json");

    try {
      int id = PathVariableExtractor.extractIntPathVariable(request, "/admin/products/:id", "id");

      Product productInput = validators.validateUpdateProduct(request, id);

      UpdateProductUseCase useCase = new UpdateProductUseCase();

      Product updatedProduct = useCase.execute(productInput);

      if (updatedProduct != null) {

        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Product updated", updatedProduct);

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }

    } catch (ValidationException e) {

      JsonResponse jsonRes =
          new JsonResponse(422, e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (NotFoundException e) {

      JsonResponse jsonRes =
       new JsonResponse(
        HttpServletResponse.SC_NOT_FOUND,
        e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (Exception e) {

      e.printStackTrace();

      JsonResponse jsonRes =
          new JsonResponse(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
             e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }
}
