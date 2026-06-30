package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Sale.CustomerReportDTO;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.GetSalesByCustomerReportUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("admin/reports/sales-by-customer")
public class AdminCustomerReportController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");

    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");

    if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {

      JsonResponse jsonRes =
          new JsonResponse(
              HttpServletResponse.SC_BAD_REQUEST,
              "Missing required parameters: startDate and endDate");

      response.setStatus(jsonRes.getStatus());
      response.getWriter().write(jsonRes.toJson());
      return;
    }

    try {

      GetSalesByCustomerReportUseCase useCase = new GetSalesByCustomerReportUseCase();
      List<CustomerReportDTO> report = useCase.execute(startDate, endDate);

      JsonResponse jsonRes =
          new JsonResponse(HttpServletResponse.SC_OK, "Report generated successfully", report);

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
