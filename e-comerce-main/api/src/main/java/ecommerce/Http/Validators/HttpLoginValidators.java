package ecommerce.Http.Validators;

import ecommerce.Database.Entites.User;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.BodyJsonToObject;
import ecommerce.Http.IO.Requests.LoginBodyRequest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpLoginValidators {

  public User validateLogin(HttpServletRequest request) throws ValidationException {
    List<String> errors = new ArrayList<String>();

    LoginBodyRequest body = null;

    try {
      body = BodyJsonToObject.parse(request, LoginBodyRequest.class);
    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
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
    user.setLogin(body.login);
    user.setSenha(body.password);
    return user;
  }
}
