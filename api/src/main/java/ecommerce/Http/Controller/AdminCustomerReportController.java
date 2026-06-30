package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Sale.CustomerReportDTO;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.GetSalesByCustomerReportUseCase;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/reports/sales-by-customer")
public class AdminCustomerReportController extends HttpServlet {

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");

    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");

    if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
      sendJsonError(
          response,
          HttpServletResponse.SC_BAD_REQUEST,
          "Missing required parameters: startDate and endDate");
      return;
    }

    if (!isValidDate(startDate) || !isValidDate(endDate)) {
      sendJsonError(
          response,
          HttpServletResponse.SC_BAD_REQUEST,
          "Parameters startDate and endDate must be in the format yyyy-MM-dd and be valid calendar dates.");
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
      sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  private boolean isValidDate(String dateStr) {
    try {
      LocalDate.parse(dateStr, DATE_FORMATTER);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  private void sendJsonError(HttpServletResponse response, int status, String message)
      throws IOException {
    response.setContentType("application/json");
    JsonResponse jsonRes = new JsonResponse(status, message);
    response.setStatus(jsonRes.getStatus());
    response.getWriter().write(jsonRes.toJson());
  }
}
