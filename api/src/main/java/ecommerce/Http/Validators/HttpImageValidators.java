package ecommerce.Http.Validators;

import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.BodyFormDataToObject;
import ecommerce.Http.IO.Requests.ImageBodyRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpImageValidators {

  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

  public ImageBodyRequest validateCreateImage(HttpServletRequest request)
      throws ValidationException {
    List<String> errors = new ArrayList<String>();

    ImageBodyRequest body = null;

    try {
      body = BodyFormDataToObject.parse(request, ImageBodyRequest.class);
    } catch (Exception e) {
      throw new ValidationException("Invalid form data: " + e.getMessage());
    }

    if (body.image == null || body.image.isEmpty()) {
      errors.add("Image is required");
    } else if (!isValidMimeType(body.image)) {
      errors.add("Only JPG, PNG, GIF and WebP formats are allowed");
    } else if (!isValidFileSize(body.image)) {
      errors.add("File size must not exceed 5MB");
    }

    String errorMessage = String.join(", ", errors);
    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    return body;
  }

  private boolean isValidMimeType(String imageBase64) {
    if (imageBase64.contains(",")) {
      String mimeType = imageBase64.split(",")[0].toLowerCase();
      return mimeType.contains("image/jpeg")
          || mimeType.contains("image/jpg")
          || mimeType.contains("image/png")
          || mimeType.contains("image/gif")
          || mimeType.contains("image/webp");
    }
    return false;
  }

  private boolean isValidFileSize(String imageBase64) {
    try {
      String base64Data = imageBase64.contains(",") ? imageBase64.split(",")[1] : imageBase64;
      byte[] fileInBytes = Base64.getDecoder().decode(base64Data);
      return fileInBytes.length <= MAX_FILE_SIZE;
    } catch (Exception e) {
      return false;
    }
  }
}
