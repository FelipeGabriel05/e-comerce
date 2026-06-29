package ecommerce.Database.Queries;

public class SaleQueries {
  public static String insertSaleQuery = "INSERT INTO venda (usuario_id) VALUES (?)";

  public static String insertSaleItemQuery =
      "INSERT INTO venda_produto (venda_id, produto_id, preco, quantidade) VALUES (?, ?, ?, ?)";

  public static String deleteSaleItemsBySaleIdQuery =
      "DELETE FROM venda_produto WHERE venda_id = ?";

  public static String deleteSaleByIdQuery = "DELETE FROM venda WHERE id = ?";
}
