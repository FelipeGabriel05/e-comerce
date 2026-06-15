package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Product;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.ListProductsUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products")
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
}
