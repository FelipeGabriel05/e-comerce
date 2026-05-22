package ecommerce.Http.IO;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class BodyFormDataToObject {

  public static <T> T parse(HttpServletRequest request, Class<T> clazz)
      throws IOException,
          NoSuchMethodException,
          InvocationTargetException,
          ServletException,
          IllegalAccessException,
          InstantiationException {
    T instance = clazz.getDeclaredConstructor().newInstance();

    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      String fieldName = field.getName();

      try {
        Part part = request.getPart(fieldName);
        if (part == null) {
          continue;
        }

        if (isFileField(part)) {
          byte[] fileBytes = part.getInputStream().readAllBytes();
          String base64String = Base64.getEncoder().encodeToString(fileBytes);
          String mimeType = part.getContentType();
          String dataUrl = "data:" + mimeType + ";base64," + base64String;
          field.set(instance, dataUrl);
          continue;
        }

        String value = new String(part.getInputStream().readAllBytes());
        convertAndSetField(field, instance, value);
      } catch (ServletException e) {
      }
    }

    return instance;
  }

  private static boolean isFileField(Part part) {
    String contentType = part.getContentType();
    return contentType != null && !contentType.isEmpty();
  }

  private static void convertAndSetField(Field field, Object instance, String value)
      throws IllegalAccessException {
    Class<?> fieldType = field.getType();

    if (fieldType == String.class) {
      field.set(instance, value);
    } else if (fieldType == int.class || fieldType == Integer.class) {
      field.set(instance, Integer.parseInt(value));
    } else if (fieldType == double.class || fieldType == Double.class) {
      field.set(instance, Double.parseDouble(value));
    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
      field.set(instance, Boolean.parseBoolean(value));
    } else if (fieldType == long.class || fieldType == Long.class) {
      field.set(instance, Long.parseLong(value));
    }
  }
}
