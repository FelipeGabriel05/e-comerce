package ecommerce.Http.Controller;

import ecommerce.Database.Entites.Sale.OutOfStockProductReportDTO;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.Http.IO.converter.DataArrayConverter;
import ecommerce.UseCases.ListOutOfStockProductsUseCase;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/reports/out-of-stock-products/export")
public class AdminOutOfStockProductsReportExportController extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
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

      ListOutOfStockProductsUseCase useCase = new ListOutOfStockProductsUseCase();
      List<OutOfStockProductReportDTO> reportData = useCase.execute();

      byte[] bytes =
          DataArrayConverter.convert(reportData, OutOfStockProductReportDTO.class, mimetype);

      response.setContentType(mimetype);
      response.setContentLength(bytes.length);
      response.setHeader(
          "Content-Disposition",
          "attachment; filename=\"out_of_stock_products_report." + extension + "\"");

      try (ServletOutputStream out = response.getOutputStream()) {
        out.write(bytes);
      }

    } catch (ValidationException e) {
      sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

    } catch (Exception e) {
      e.printStackTrace();
      sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
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
