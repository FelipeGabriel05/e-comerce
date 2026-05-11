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

    if (body.name == null || body.name.isEmpty()) {
      errors.add("Name is required");
    }

    if (body.address == null || body.address.isEmpty()) {
      errors.add("Address is required");
    }

    if (body.email == null || body.email.isEmpty()) {
      errors.add("Email is required");
    }

    if (body.login == null || body.login.isEmpty()) {
      errors.add("Login is required");
    }

    if (body.password == null || body.password.isEmpty()) {
      errors.add("Password is required");
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
