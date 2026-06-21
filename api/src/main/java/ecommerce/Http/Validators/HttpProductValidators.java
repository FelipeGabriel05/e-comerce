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

    if (body.foto != null && !body.foto.isEmpty() && !body.foto.contains(";base64,")) {
      errors.add("Photo must be a valid image");
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

  public Product validateUpdateProduct(HttpServletRequest request, int id)
      throws ValidationException {

    List<String> errors = new ArrayList<String>();

    ProductBodyRequest body = null;

    try {

      body = BodyFormDataToObject.parse(request, ProductBodyRequest.class);

    } catch (Exception e) {

      throw new ValidationException("Invalid body data");
    }

    if (body.descricao == null
        || body.descricao.trim().length() < 10
        || body.descricao.trim().length() > 300) {

      errors.add("Description must have between 10 and 300 characters");
    }

    if (body.quantidade <= 0) {
      errors.add("Quantity must be greater than zero");
    }

    if (body.preco < 0) {
      errors.add("Price cannot be negative");
    }

    if (body.categoriaId <= 0) {
      errors.add("Category ID is required");
    }

    if (body.foto != null
        && !body.foto.isEmpty()
        && !body.foto.startsWith("http")
        && !body.foto.contains(";base64,")) {
      errors.add("Photo must be a URL or a valid image");
    }

    if (!errors.isEmpty()) {
      String errorMessage = String.join(", ", errors);
      throw new ValidationException(errorMessage);
    }

    Product product = new Product();

    product.setId(id);
    product.setDescricao(body.descricao);
    product.setPreco(body.preco);
    product.setFoto(body.foto);
    product.setQuantidade(body.quantidade);
    product.setCategoriaId(body.categoriaId);

    return product;
  }

  public Product validateStock(HttpServletRequest request, int id) throws ValidationException {

    List<String> errors = new ArrayList<>();

    ProductBodyRequest body = null;

    try {
      body = BodyFormDataToObject.parse(request, ProductBodyRequest.class);

    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
    }

    if (body.quantidade <= 0) {
      errors.add("Quantity must be greater than zero");
    }

    if (!errors.isEmpty()) {
      String errorMessage = String.join(", ", errors);

      throw new ValidationException(errorMessage);
    }

    Product product = new Product();

    product.setId(id);
    product.setQuantidade(body.quantidade);

    return product;
  }

  public Product validateUpdateProductQuantity(HttpServletRequest request, int id)
      throws ValidationException {

    List<String> errors = new ArrayList<>();

    ProductBodyRequest body = null;

    try {
      body = BodyFormDataToObject.parse(request, ProductBodyRequest.class);

    } catch (Exception e) {
      throw new ValidationException("Invalid body data");
    }

    if (body.quantidade < 0) {
      errors.add("Quantity cannot be negative");
    }

    if (!errors.isEmpty()) {
      String errorMessage = String.join(", ", errors);

      throw new ValidationException(errorMessage);
    }

    Product product = new Product();

    product.setId(id);
    product.setQuantidade(body.quantidade);

    return product;
  }
}
