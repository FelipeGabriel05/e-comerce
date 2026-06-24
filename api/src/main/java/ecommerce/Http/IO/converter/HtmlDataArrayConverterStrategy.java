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
    List<String> headers = new ArrayList<>(data.get(0).keySet());
    StringBuilder sb = new StringBuilder();
    sb.append("<table><thead><tr>");
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
