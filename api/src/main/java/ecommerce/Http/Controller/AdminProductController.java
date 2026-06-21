package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Product;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ProductInUseException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.PathVariableExtractor;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpProductValidators;
import ecommerce.UseCases.CreateProductUseCase;
import ecommerce.UseCases.DeleteProductUseCase;
import ecommerce.UseCases.UpdateProductUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/products/*")
@MultipartConfig
public class AdminProductController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public static final int SC_UNPROCESSABLE_ENTITY = 422;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpProductValidators validators = new HttpProductValidators();
    response.setContentType("application/json");

    try {
      Product productInput = validators.validateCreateProduct(request);

      CreateProductUseCase createProductUseCase = new CreateProductUseCase();
      Product createdProduct = createProductUseCase.execute(productInput);

      if (createdProduct != null) {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_CREATED, "Product created", createdProduct);

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      } else {
        JsonResponse jsonRes =
            new JsonResponse(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create product");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }

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

      String baseUrl =
          request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

      UpdateProductUseCase useCase = new UpdateProductUseCase();

      Product updatedProduct = useCase.execute(productInput, baseUrl);

      if (updatedProduct != null) {

        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Product updated", updatedProduct);

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      } else {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_NOT_FOUND, "Product not found");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }

    } catch (ValidationException e) {

      JsonResponse jsonRes = new JsonResponse(SC_UNPROCESSABLE_ENTITY, e.getMessage());

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

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    HttpProductValidators validators = new HttpProductValidators();

    try {

      int id = validators.validateDeleteProduct(request);

      DeleteProductUseCase useCase = new DeleteProductUseCase();

      boolean isDeleted = useCase.execute(id);

      if (isDeleted) {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Product deleted successfully");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());

      } else {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_NOT_FOUND, "Product not found");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }

    } catch (ProductInUseException e) {

      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_CONFLICT, e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (ValidationException e) {

      JsonResponse jsonRes = new JsonResponse(SC_UNPROCESSABLE_ENTITY, e.getMessage());

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (Exception e) {

      e.printStackTrace();

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
    }
  }
}
