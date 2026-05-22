package ecommerce.UseCases;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class GetImageUseCase {

  public File execute(String filename) throws IOException {
    File baseDir = new File(UploadImageUseCase.UPLOAD_FOLDER).getCanonicalFile();
    File file = new File(baseDir, filename).getCanonicalFile();

    Path basePath = baseDir.toPath();
    Path filePath = file.toPath();

    if (!filePath.startsWith(basePath)) {
      return null;
    }

    if (!file.exists() || !file.isFile()) {
      return null;
    }

    return file;
  }
}
