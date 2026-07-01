package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Sale.OutOfStockProductReportDTO;
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

    try {
      ListOutOfStockProductsUseCase useCase = new ListOutOfStockProductsUseCase();

      List<OutOfStockProductReportDTO> report = useCase.execute();

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Report generated successfully", report);

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());

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
