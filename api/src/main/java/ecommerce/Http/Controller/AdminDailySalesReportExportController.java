package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Sale.DailySalesReportDTO;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.IO.converter.DataArrayConverter;
import ecommerce.Http.Validators.HttpReportValidators;
import ecommerce.UseCases.GetDailySalesReportUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/reports/daily-sales/export")
public class AdminDailySalesReportExportController extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpReportValidators validators = new HttpReportValidators();

    try {
      validators.validateReportExportParams(request);

      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");
      String format = request.getParameter("format");

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

      GetDailySalesReportUseCase useCase = new GetDailySalesReportUseCase();
      List<DailySalesReportDTO> reportData = useCase.execute(startDate, endDate);

      byte[] bytes = DataArrayConverter.convert(reportData, DailySalesReportDTO.class, mimetype);

      response.setContentType(mimetype);
      response.setContentLength(bytes.length);
      response.setHeader(
          "Content-Disposition", "attachment; filename=\"daily_sales_report." + extension + "\"");

      try (ServletOutputStream out = response.getOutputStream()) {
        out.write(bytes);
      }

    } catch (ValidationException e) {
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response
          .getWriter()
          .write(new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()).toJson());

    } catch (Exception e) {
      e.printStackTrace();
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response
          .getWriter()
          .write(
              new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage())
                  .toJson());
    }
  }
}
