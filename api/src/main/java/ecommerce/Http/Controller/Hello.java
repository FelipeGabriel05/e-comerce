package ecommerce.Http.Controller;

import ecommerce.Database.Entites.User;
import ecommerce.Http.Validators.HttpUserValidators;
import ecommerce.UseCases.CreateUserUseCase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class Hello extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public Hello() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    response.getWriter().write("{\"message\": \"Hello, World!\"}");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    HttpUserValidators validators = new HttpUserValidators();

    try {
      User userInput = validators.validateCreateUser(request);
      CreateUserUseCase createUserUseCase = new CreateUserUseCase();
      User user = createUserUseCase.execute(userInput);
      response.setContentType("application/json");
      if (user != null) response.getWriter().write(user.toJson());
      else response.getWriter().write("{\"error\": \"User creation failed\"}");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
