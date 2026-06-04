package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Category;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpCategoryValidators;
import ecommerce.UseCases.CreateCategoryUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/category")
public class CategoryController extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    HttpCategoryValidators validators = new HttpCategoryValidators();

    try {

      Category categoryInput = validators.validateCreateCategory(request);

      CreateCategoryUseCase useCase = new CreateCategoryUseCase();

      Category createdCategory = useCase.execute(categoryInput);

      if (createdCategory != null) {

        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_CREATED, "Category created", createdCategory);

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());

      } else {

        JsonResponse jsonRes =
            new JsonResponse(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create category");

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
}
