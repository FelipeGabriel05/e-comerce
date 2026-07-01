package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Sale.OutOfStockProductReportDTO;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.ListOutOfStockProductsUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/reports/out-of-stock-products")
public class AdminOutOfStockProductsReportController extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    HttpReportValidators validators = new HttpReportValidators();
    try {

      validators.validateReportParams(request);

      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");

      ListOutOfStockProductsUseCase useCase = new ListOutOfStockProductsUseCase();
      List<OutOfStockProductReportDTO> report = useCase.execute(startDate, endDate);

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Report generated successfully", report);

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

    } catch (ValidationException e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response
          .getWriter()
          .write(new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()).toJson());

    } catch (Exception e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response
          .getWriter()
          .write(
              new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage())
                  .toJson());
    }
  }
}
