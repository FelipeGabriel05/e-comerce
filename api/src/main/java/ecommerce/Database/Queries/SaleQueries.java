package ecommerce.Database.Queries;

public class SaleQueries {
  public static String insertSaleQuery = "INSERT INTO venda (usuario_id) VALUES (?)";

  public static String insertSaleItemQuery =
      "INSERT INTO venda_produto (venda_id, produto_id, preco, quantidade) VALUES (?, ?, ?, ?)";

  public static String selectSalesByUserIdQuery =
      "SELECT id, data_hora, usuario_id FROM venda WHERE usuario_id = ? ORDER BY data_hora DESC";

  public static String selectSaleItemsBySaleIdQuery =
      "SELECT produto_id, preco, quantidade FROM venda_produto WHERE venda_id = ?";
}
