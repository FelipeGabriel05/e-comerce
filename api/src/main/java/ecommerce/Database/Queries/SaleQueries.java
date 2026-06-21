package ecommerce.Database.Queries;

public class SaleQueries {

  public static String insertSaleQuery =
      "INSERT INTO venda (data_hora, usuario_id) "
          + "VALUES (CURRENT_TIMESTAMP, ?) "
          + "RETURNING id";

  public static String insertSaleItemQuery =
      "INSERT INTO venda_produto "
          + "(venda_id, produto_id, preco, quantidade) "
          + "VALUES (?, ?, ?, ?)";
}
