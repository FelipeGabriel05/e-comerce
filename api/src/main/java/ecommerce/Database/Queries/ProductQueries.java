package ecommerce.Database.Queries;

public class ProductQueries {
  public static String insertProductQuery =
      "INSERT INTO produto (descricao, preco, foto, quantidade, categoria_id) VALUES (?, ?, ?, ?, ?)";

  public static String listAvailableProductsQuery =
      "SELECT id, descricao, preco, foto, quantidade, categoria_id "
          + "FROM produto WHERE quantidade > 0";
}
