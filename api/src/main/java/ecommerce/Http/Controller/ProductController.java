package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Product;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpProductValidators;
import ecommerce.UseCases.CreateProductUseCase;
import ecommerce.UseCases.ListProductsUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/products")
@MultipartConfig
public class ProductController extends HttpServlet {

  private static final long serialVersionUID = 1L;

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
}
