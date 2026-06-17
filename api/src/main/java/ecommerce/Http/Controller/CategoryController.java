package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Category;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.ListCategoriesUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/category")
public class CategoryController extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");

    try {

      ListCategoriesUseCase useCase = new ListCategoriesUseCase();
      List<Category> categories = useCase.execute();

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Categories listed", categories);

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
