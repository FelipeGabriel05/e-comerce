package ecommerce.Http.Validators;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpUserValidators {

  public static boolean isValidEmail(String email) {

    String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    return email != null && email.matches(regex);
  }


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

  public int validateDeleteUser(HttpServletRequest request) throws ValidationException {
    List<String> errors = new ArrayList<String>();

    String idParam = request.getParameter("id");

    if (idParam == null || idParam.isEmpty()) {
      errors.add("ID is required");
    }

    int id = 0;

    if (errors.isEmpty()) {
      try {
        id = Integer.parseInt(idParam);

        if (id <= 0) {
          errors.add("ID must be greater than zero");
        }

      } catch (NumberFormatException e) {
        errors.add("ID must be a valid number");
      }
    }

    String errorMessage = String.join(", ", errors);
    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    return id;
  }

  public User validateUpdateUser(HttpServletRequest request) throws ValidationException {
    List<String> errors = new ArrayList<>();

     String idParam = request.getParameter("id");

    if (idParam == null || idParam.isEmpty()) {
      errors.add("ID is required");
    }

    int id = 0;

    if (errors.isEmpty()) {
      try {
        id = Integer.parseInt(idParam);

        if (id <= 0) {
          errors.add("ID must be greater than zero");
        }

      } catch (NumberFormatException e) {
        errors.add("ID must be a valid number");
      }
    }

    String name = request.getParameter("name");
    if (name == null || name.isEmpty()) {
      errors.add("Name is required");
    } else if (name.length() < 3) {
      errors.add("Name must have at least 3 characters");
    }

    String address = request.getParameter("address");
    if (address == null || address.isEmpty()) {
      errors.add("Address is required");
    } else if (address.split(" ").length < 3) {
      errors.add("Address must contain at least 3 words");
    }

    String email = request.getParameter("email");
    if (email == null || email.isEmpty()) {
      errors.add("Email is required");
    } else if (!isValidEmail(email)) {
      errors.add("Email is not valid");
    }

    String login = request.getParameter("login");
    if (login == null || login.isEmpty()) {
      errors.add("Login is required");
    } else if (login.length() < 4) {
      errors.add("Login must have at least 4 characters");
    }

    String password = request.getParameter("password");
    if (password == null || password.isEmpty()) {
      errors.add("Password is required");
    } else {
      if (password.length() < 6) {
        errors.add("Password must have at least 6 characters");
      }
      if (!password.matches(".*[A-Z].*")) {
        errors.add("Password must contain at least one uppercase letter");
      }
      if (!password.matches(".*\\d.*")) {
        errors.add("Password must contain at least one number");
      }
    }

    String errorMessage = String.join(", ", errors);
    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    User user = new User();
    user.setId(id);
    user.setNome(name);
    user.setEndereco(address);
    user.setEmail(email);
    user.setLogin(login);
    user.setSenha(password);

    return user;
  }
}
