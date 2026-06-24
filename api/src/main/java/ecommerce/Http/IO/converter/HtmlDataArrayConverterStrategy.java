package ecommerce.Http.IO.converter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlDataArrayConverterStrategy implements DataArrayConverterStrategy {

  @Override
  public byte[] convert(List<Map<String, Object>> data) {
    return buildHtmlTable(data).getBytes(StandardCharsets.UTF_8);
  }

  String buildHtmlTable(List<Map<String, Object>> data) {
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">")
        .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
        .append(
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB\" crossorigin=\"anonymous\">")
        .append("</head><body class=\"p-4\">")
        .append(buildTableFragment(data))
        .append("</body></html>");
    return sb.toString();
  }

  String buildTableFragment(List<Map<String, Object>> data) {
    List<String> headers = new ArrayList<>(data.get(0).keySet());
    StringBuilder sb = new StringBuilder();
    sb.append(
        "<table class=\"table table-striped table-hover table-bordered\"><thead class=\"table-dark\"><tr>");
    for (String header : headers) {
      sb.append("<th>").append(escapeHtml(header)).append("</th>");
    }
    sb.append("</tr></thead><tbody>");
    for (Map<String, Object> row : data) {
      sb.append("<tr>");
      for (String header : headers) {
        Object value = row.get(header);
        sb.append("<td>").append(value != null ? escapeHtml(value.toString()) : "").append("</td>");
      }
      sb.append("</tr>");
    }
    sb.append("</tbody></table>");
    return sb.toString();
  }

  private String escapeHtml(String value) {
    return value
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;");
  }
}
