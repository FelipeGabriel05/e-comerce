package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Sale.CustomerReportDTO;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.IO.converter.DataArrayConverter;
import ecommerce.UseCases.GetSalesByCustomerReportUseCase;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/reports/sales-by-customer/export")
public class AdminCustomerReportExportController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String format = request.getParameter("format");

    if (!isValidDate(startDate) || !isValidDate(endDate)) {
      sendJsonError(
          response,
          HttpServletResponse.SC_BAD_REQUEST,
          "Parameters startDate and endDate must be in the format yyyy-MM-dd and be valid calendar dates.");
      return;
    }

    if (format != null && !ALLOWED_FORMATS.contains(format.toLowerCase())) {
      sendJsonError(
          response,
          HttpServletResponse.SC_BAD_REQUEST,
          "Invalid format. Allowed values are: " + ALLOWED_FORMATS);
      return;
    }

    String mimetype = "application/pdf";
    String extension = "pdf";

    if (format != null) {
      switch (format.toLowerCase()) {
        case "csv":
          mimetype = "text/plain";
          extension = "csv";
          break;
        case "html":
          mimetype = "text/html";
          extension = "html";
          break;
        case "pdf":
        default:
          mimetype = "application/pdf";
          extension = "pdf";
          break;
      }
    }

    try {
      GetSalesByCustomerReportUseCase useCase = new GetSalesByCustomerReportUseCase();
      List<CustomerReportDTO> reportData = useCase.execute(startDate, endDate);

      byte[] bytes = DataArrayConverter.convert(reportData, CustomerReportDTO.class, mimetype);

      response.setContentType(mimetype);
      response.setContentLength(bytes.length);
      response.setHeader(
          "Content-Disposition",
          "attachment; filename=\"customer_sales_report." + extension + "\"");

      try (ServletOutputStream out = response.getOutputStream()) {
        out.write(bytes);
      }

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
