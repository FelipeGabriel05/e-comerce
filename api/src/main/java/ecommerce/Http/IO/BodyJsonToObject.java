package ecommerce.Http.IO;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;

public class BodyJsonToObject {

  public static <T> T parse(HttpServletRequest request, Class<T> clazz) throws IOException {
    String requestBody =
        new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    Gson gson = new Gson();
    return gson.fromJson(requestBody, clazz);
  }
}
