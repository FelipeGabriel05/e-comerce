package ecommerce.Http.IO.converter;

import java.util.List;
import java.util.Map;

public interface DataArrayConverterStrategy {
  byte[] convert(List<Map<String, Object>> data);
}
