package ecommerce.Http.IO.Responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonResponse {
  private int status;
  private String message;
  private Object data;

  public JsonResponse(int status, String message, Object data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public JsonResponse(int status, String message) {
    this(status, message, null);
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Object getData() {
    return data;
  }

  public String toJson() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.toJson(this);
  }
}
