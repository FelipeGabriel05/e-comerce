package ecommerce.Database.Queries;

public class SaleQueries {
  public static String insertSaleQuery = "INSERT INTO venda (usuario_id) VALUES (?)";

  public static String insertSaleItemQuery =
      "INSERT INTO venda_produto (venda_id, produto_id, preco, quantidade) VALUES (?, ?, ?, ?)";

  public static String createSaleQuery =
      "INSERT INTO venda (data_hora, usuario_id) VALUES (CURRENT_TIMESTAMP, ?) RETURNING id";

  public static String createSaleProductQuery =
      "INSERT INTO venda_produto (venda_id, produto_id, preco, quantidade) "
          + "VALUES (?, ?, ?, ?)";
}
