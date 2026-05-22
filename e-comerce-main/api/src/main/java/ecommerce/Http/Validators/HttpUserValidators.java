package ecommerce.Http.Validators;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpUserValidators {

  public User validateCreateUser(HttpServletRequest request) throws ValidationException {
    List<String> errors = new ArrayList<String>();

    if (request.getParameter("name") == null || request.getParameter("name").isEmpty()) {
      errors.add("Name is required");
    }

    if (request.getParameter("address") == null || request.getParameter("address").isEmpty()) {
      errors.add("Address is required");
    }

    if (request.getParameter("email") == null || request.getParameter("email").isEmpty()) {
      errors.add("Email is required");
    }

    if (request.getParameter("login") == null || request.getParameter("login").isEmpty()) {
      errors.add("Login is required");
    }

    if (request.getParameter("password") == null || request.getParameter("password").isEmpty()) {
      errors.add("Password is required");
    }

    String errorMessage = String.join(", ", errors);
    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    User user = new User();
    user.setNome(request.getParameter("name"));
    user.setEndereco(request.getParameter("address"));
    user.setEmail(request.getParameter("email"));
    user.setLogin(request.getParameter("login"));
    user.setSenha(request.getParameter("password"));
    return user;
  }
}
