package ecommerce.Http.IO.converter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlainTextDataArrayConverterStrategy implements DataArrayConverterStrategy {

  @Override
  public byte[] convert(List<Map<String, Object>> data) {
    List<String> headers = new ArrayList<>(data.get(0).keySet());
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < headers.size(); i++) {
      if (i > 0) sb.append(",");
      sb.append(escapeCsvField(headers.get(i)));
    }
    sb.append("\n");

    for (Map<String, Object> row : data) {
      for (int i = 0; i < headers.size(); i++) {
        if (i > 0) sb.append(",");
        Object value = row.get(headers.get(i));
        sb.append(value != null ? escapeCsvField(value.toString()) : "");
      }
      sb.append("\n");
    }

    return sb.toString().getBytes(StandardCharsets.UTF_8);
  }

  private String escapeCsvField(String value) {
    if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
      return "\"" + value.replace("\"", "\"\"") + "\"";
    }
    return value;
  }
}
