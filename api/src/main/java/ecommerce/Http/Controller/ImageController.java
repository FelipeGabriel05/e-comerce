package ecommerce.Http.Controller;

import ecommerce.Http.IO.Responses.JsonResponse;
import ecommerce.UseCases.GetImageUseCase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/image/*")
@MultipartConfig
public class ImageController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public ImageController() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    if (pathInfo == null || pathInfo.equals("/")) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    String filename = pathInfo.substring(1);
    GetImageUseCase getImageUseCase = new GetImageUseCase();
    File file = getImageUseCase.execute(filename);

    if (file == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    String mimeType = getServletContext().getMimeType(file.getName());
    if (mimeType == null) mimeType = "application/octet-stream";

    response.setContentType(mimeType);
    response.setContentLengthLong(file.length());

    try (FileInputStream in = new FileInputStream(file)) {
      in.transferTo(response.getOutputStream());
    }
    response.getOutputStream().flush();
    response.setHeader("Cache-Control", "public, max-age=86400");
  }
}
