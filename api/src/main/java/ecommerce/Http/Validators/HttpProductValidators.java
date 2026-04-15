package ecommerce.Http.Validators;

import ecommerce.Database.Entites.Product;
import ecommerce.Exceptions.ValidationException;
import ecommerce.Http.IO.BodyFormDataToObject;
import ecommerce.Http.IO.Requests.ProductBodyRequest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class HttpProductValidators {

  public Product validateCreateProduct(HttpServletRequest request) throws ValidationException {
    List<String> errors = new ArrayList<String>();

    ProductBodyRequest body = null;

    try {
      body = BodyFormDataToObject.parse(request, ProductBodyRequest.class);
    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
    }

    if (body.descricao == null || body.descricao.isEmpty()) {
      errors.add("Description is required");
    }

    if (body.quantidade <= 0) {
      errors.add("Quantity must be greater than zero");
    }

    if (body.preco <= 0) {
      errors.add("Price must be greater than zero");
    }

    if (body.categoriaId <= 0) {
      errors.add("Category ID is required");
    }

    String errorMessage = String.join(", ", errors);
    if (!errorMessage.isEmpty()) {
      throw new ValidationException(errorMessage);
    }

    Product product = new Product();
    product.setDescricao(body.descricao);
    product.setPreco(body.preco);
    product.setFoto(body.foto);
    product.setQuantidade(body.quantidade);
    product.setCategoriaId(body.categoriaId);
    return product;
  }
}
