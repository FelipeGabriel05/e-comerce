package ecommerce.Http.Controller;

import ecommerce.Exceptions.NotFoundException;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.PathVariableExtractor;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.DeleteSaleUseCase;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/sales/*")
public class AdminSaleController extends HttpServlet {

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    try {
      int id = PathVariableExtractor.extractIntPathVariable(request, "/admin/sales/:id", "id");

      DeleteSaleUseCase useCase = new DeleteSaleUseCase();

      useCase.execute(id);

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Sale deleted successfully");

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (ValidationException e) {

      JsonResponse jsonRes = new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

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
