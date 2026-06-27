package ecommerce.Http.IO.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataArrayConverter {

  public static <T> byte[] convert(List<T> data, Class<T> clazz, String contentType) {
    if (data == null || data.isEmpty()) {
      return new byte[0];
    }
    DataArrayConverterStrategy strategy =
        switch (contentType) {
          case "text/html" -> new HtmlDataArrayConverterStrategy();
          case "text/plain" -> new PlainTextDataArrayConverterStrategy();
          case "application/pdf" -> new PdfDataArrayConverterStrategy();
          default -> throw new IllegalArgumentException("Unsupported content type: " + contentType);
        };
    return strategy.convert(toMaps(data, clazz));
  }

  private static <T> List<Map<String, Object>> toMaps(List<T> data, Class<T> clazz) {
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) field.setAccessible(true);
    List<Map<String, Object>> rows = new ArrayList<>(data.size());
    for (T item : data) {
      Map<String, Object> row = new LinkedHashMap<>();
      for (Field field : fields) {
        try {
          row.put(field.getName(), field.get(item));
        } catch (IllegalAccessException e) {
          row.put(field.getName(), null);
        }
      }
      rows.add(row);
    }
    return rows;
  }
}
