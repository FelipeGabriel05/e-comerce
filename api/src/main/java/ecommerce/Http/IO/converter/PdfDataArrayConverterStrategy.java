package ecommerce.Http.IO.converter;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PdfDataArrayConverterStrategy implements DataArrayConverterStrategy {

  @Override
  public byte[] convert(List<Map<String, Object>> data) {
    String htmlTable = new HtmlDataArrayConverterStrategy().buildHtmlTable(data);
    String xhtml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
            + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
            + "<meta charset=\"UTF-8\"/><title>Report</title>"
            + "<style>table{border-collapse:collapse;width:100%}"
            + "th,td{border:1px solid #ccc;padding:4px 8px;text-align:left}"
            + "thead{background:#f0f0f0}</style>"
            + "</head><body>"
            + htmlTable
            + "</body></html>";
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
}
