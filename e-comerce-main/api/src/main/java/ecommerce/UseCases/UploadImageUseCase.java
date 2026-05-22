package ecommerce.UseCases;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class UploadImageUseCase {

  public static final String UPLOAD_FOLDER =
      File.separator
          + "var"
          + File.separator
          + "lib"
          + File.separator
          + "jetty"
          + File.separator
          + "public";

  private final String uploadFolder = UPLOAD_FOLDER;

  public String execute(String imageBase64) {
    try {
      String base64Data = imageBase64.contains(",") ? imageBase64.split(",")[1] : imageBase64;
      byte[] fileInBytes = Base64.getDecoder().decode(base64Data);

      File uploadDir = new File(uploadFolder);
      if (!uploadDir.exists()) {
        Files.createDirectories(Paths.get(uploadFolder));
      }

      String extension = getExtensionFromMimeType(imageBase64);
      String filename = UUID.randomUUID().toString() + extension;
      String filepath = uploadFolder + File.separator + filename;
      System.out.println(filepath);

      try (FileOutputStream outputStream = new FileOutputStream(filepath)) {
        outputStream.write(fileInBytes);
        outputStream.flush();
      }

      File savedFile = new File(filepath);
      if (savedFile.exists() && savedFile.length() > 0) {
        return filename;
      } else {
        System.err.println("File was not saved properly: " + filepath);
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private String getExtensionFromMimeType(String imageBase64) {
    if (imageBase64.contains(",")) {
      String mimeType = imageBase64.split(",")[0].toLowerCase();
      if (mimeType.contains("jpeg") || mimeType.contains("jpg")) {
        return ".jpg";
      } else if (mimeType.contains("png")) {
        return ".png";
      } else if (mimeType.contains("gif")) {
        return ".gif";
      } else if (mimeType.contains("webp")) {
        return ".webp";
      }
    }
    return ".png";
  }
}
