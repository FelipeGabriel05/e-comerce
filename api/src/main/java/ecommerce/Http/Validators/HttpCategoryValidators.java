package ecommerce.Http.Validators;

import ecommerce.Database.Entites.Category;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.BodyJsonToObject;
import ecommerce.Http.IO.Requests.CreateCategoryBodyRequest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpCategoryValidators {

  public Category validateCreateCategory(HttpServletRequest request) throws ValidationException {

    List<String> errors = new ArrayList<>();

    CreateCategoryBodyRequest body = null;

    try {
      body = BodyJsonToObject.parse(request, CreateCategoryBodyRequest.class);
    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
    }

    if (body.descricao == null || body.descricao.isBlank()) {
      errors.add("Description is required");
    } else {

      if (body.descricao.length() < 3) {
        errors.add("Description must contain at least 3 characters");
      }
      if (body.descricao.length() > 255) {
        errors.add("Description must contain at most 255 characters");
      }
    }

    String errorMessage = String.join(", ", errors);

    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    Category category = new Category();
    category.setDescricao(body.descricao);

    return category;
  }
}
