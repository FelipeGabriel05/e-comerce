package ecommerce.Database.Queries;

public class ProductQueries {
  public static String insertProductQuery =
      "INSERT INTO produto (descricao, preco, foto, quantidade, categoria_id) VALUES (?, ?, ?, ?, ?)";

  public static String listAvailableProductsQuery =
      "SELECT id, descricao, preco, foto, quantidade, categoria_id "
          + "FROM produto WHERE quantidade > 0 ORDER BY id ASC";

  public static String selectProductByIdQuery = "SELECT * FROM produto WHERE id = ?";

  public static String updateProductQuery =
      "UPDATE produto SET descricao = ?, preco = ?, foto = ?, quantidade = ?, categoria_id = ? WHERE id =?";

  public static String updateProductQuantityQuery =
      "UPDATE produto SET quantidade = ? WHERE id = ?";

  public static String decreaseProductStockQuery =
      "UPDATE produto SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?";
  public static String deleteProductQuery = "DELETE FROM produto WHERE id = ?";
}
