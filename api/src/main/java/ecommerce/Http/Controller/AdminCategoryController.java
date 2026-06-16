package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Category;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.Validators.HttpCategoryValidators;
import ecommerce.UseCases.CreateCategoryUseCase;
import ecommerce.UseCases.DeleteCategoryUseCase;
import ecommerce.UseCases.UpdateCategoryUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/category")
public class AdminCategoryController extends HttpServlet {

  public static final int SC_UNPROCESSABLE_ENTITY = 422;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    HttpCategoryValidators validators = new HttpCategoryValidators();

    try {

      Category categoryInput = validators.validateCreateCategory(request);

      CreateCategoryUseCase useCase = new CreateCategoryUseCase();

      Category createdCategory = useCase.execute(categoryInput);

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_CREATED, "Category created", createdCategory);

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

  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    HttpCategoryValidators validators = new HttpCategoryValidators();

    try {

      Category categoryInput = validators.validateUpdateCategory(request);

      UpdateCategoryUseCase useCase = new UpdateCategoryUseCase();

      boolean isUpdated = useCase.execute(categoryInput);

      if (isUpdated) {
        JsonResponse jsonRes =
            new JsonResponse(
                HttpServletResponse.SC_OK, "Category updated successfully", categoryInput);

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());

      } else {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_NOT_FOUND, "Category not found");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }

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

  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");

    HttpCategoryValidators validators = new HttpCategoryValidators();

    try {

      int id = validators.validateDeleteCategory(request);

      DeleteCategoryUseCase useCase = new DeleteCategoryUseCase();

      boolean isDeleted = useCase.execute(id);

      if (isDeleted) {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_OK, "Category deleted successfully");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());

      } else {
        JsonResponse jsonRes =
            new JsonResponse(HttpServletResponse.SC_NOT_FOUND, "Category not found");

        response.setStatus(jsonRes.getStatus());
        response.getWriter().write(jsonRes.toJson());
      }

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
