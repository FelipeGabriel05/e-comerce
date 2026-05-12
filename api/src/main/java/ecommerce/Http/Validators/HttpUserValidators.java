package ecommerce.Http.Validators;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.BodyJsonToObject;
import ecommerce.Http.IO.Requests.CreateUserBodyRequest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpUserValidators {

  public User validateCreateUser(HttpServletRequest request) throws ValidationException {
    List<String> errors = new ArrayList<String>();

    CreateUserBodyRequest body = null;

    try {
      body = BodyJsonToObject.parse(request, CreateUserBodyRequest.class);
    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
    }

    if (body.name == null || body.name.isBlank()) {
      errors.add("Name is required");
    } else {
      if (body.name.length() < 3) {
        errors.add("Name must contain at least 3 characters");
      }

      if (body.name.length() > 100) {
        errors.add("Name must contain at most 100 characters");
      }
    }

    if (body.address == null || body.address.isBlank()) {
      errors.add("Address is required");
    } else {
      if (body.address.length() < 5) {
        errors.add("Address must contain at least 5 characters");
      }

      if (body.address.length() > 255) {
        errors.add("Address must contain at most 255 characters");
      }
    }

    if (body.email == null || body.email.isBlank()) {
      errors.add("Email is required");
    } else {
      String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

      if (!body.email.matches(emailRegex)) {
        errors.add("Invalid email format");
      }
    }

    if (body.login == null || body.login.isBlank()) {
      errors.add("Login is required");
    } else {
      String loginRegex = "^[a-zA-Z0-9._-]+$";

      if (body.login.length() < 4) {
        errors.add("Login must contain at least 4 characters");
      }

      if (body.login.length() > 20) {
        errors.add("Login must contain at most 20 characters");
      }

      if (!body.login.matches(loginRegex)) {
        errors.add("Login can only contain letters, numbers, dots, underscores and hyphens");
      }
    }

    if (body.password == null || body.password.isBlank()) {
      errors.add("Password is required");
    } else {
      if (body.password.length() < 8) {
        errors.add("Password must contain at least 8 characters");
      }

      if (body.password.length() > 32) {
        errors.add("Password must contain at most 32 characters");
      }

      if (!body.password.matches(".*[A-Z].*")) {
        errors.add("Password must contain at least one uppercase letter");
      }

      if (!body.password.matches(".*[a-z].*")) {
        errors.add("Password must contain at least one lowercase letter");
      }

      if (!body.password.matches(".*\\d.*")) {
        errors.add("Password must contain at least one number");
      }

      if (!body.password.matches(".*[@$!%*?&].*")) {
        errors.add("Password must contain at least one special character (@$!%*?&)");
      }
    }

    String errorMessage = String.join(", ", errors);

    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    User user = new User();
    user.setNome(body.name);
    user.setEndereco(body.address);
    user.setEmail(body.email);
    user.setLogin(body.login);
    user.setSenha(body.password);

    return user;
  }
}
