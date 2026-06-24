package ecommerce.Http.IO.converter;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PdfDataArrayConverterStrategy implements DataArrayConverterStrategy {

  @Override
  public byte[] convert(List<Map<String, Object>> data) {
    String xhtml = buildXhtml(data);
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.withHtmlContent(xhtml, null);
      builder.toStream(baos);
      builder.run();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("PDF rendering failed", e);
    }
  }

  private String buildXhtml(List<Map<String, Object>> data) {
    return new StringBuilder()
        .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        .append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" ")
        .append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">")
        .append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>")
        .append("<meta charset=\"UTF-8\"/><title>Report</title>")
        .append("<style>")
        .append("body{font-family:Arial,sans-serif;font-size:13px;margin:24px}")
        .append("table{border-collapse:collapse;width:100%}")
        .append("th,td{border:1px solid #dee2e6;padding:8px 12px;text-align:left}")
        .append("thead tr{background:#212529;color:#fff}")
        .append("tbody tr:nth-child(even){background:#f2f2f2}")
        .append("tbody tr:nth-child(odd){background:#fff}")
        .append("</style>")
        .append("</head><body>")
        .append(new HtmlDataArrayConverterStrategy().buildTableFragment(data))
        .append("</body></html>")
        .toString();
  }
}
