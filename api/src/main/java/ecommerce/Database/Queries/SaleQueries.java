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

  public static String deleteSaleItemsBySaleIdQuery =
      "DELETE FROM venda_produto WHERE venda_id = ?";

  public static String deleteSaleByIdQuery = "DELETE FROM venda WHERE id = ?";

  public static String selectSalesByUserIdQuery =
      "SELECT id, data_hora, usuario_id FROM venda WHERE usuario_id = ? ORDER BY data_hora DESC";

  public static String selectSaleItemsBySaleIdQuery =
      "SELECT vp.produto_id, vp.preco, vp.quantidade, p.descricao, p.foto, p.categoria_id "
          + "FROM venda_produto vp "
          + "JOIN produto p ON vp.produto_id = p.id "
          + "WHERE vp.venda_id = ?";

  public static String selectAllSalesQuery =
      "SELECT id, data_hora, usuario_id FROM venda ORDER BY data_hora DESC";

  public static String selectSalesByCustomerReportQuery =
      "SELECT u.id AS cliente_id, u.nome AS cliente_nome, COUNT(v.id) as qtde_compras FROM venda v JOIN usuario u ON v.usuario_id = u.id WHERE v.data_hora BETWEEN ? AND ? GROUP BY u.id, u.nome ORDER BY qtde_compras DESC";

  public static String selectDailySalesReportQuery =
      "SELECT DATE(v.data_hora) AS venda_data, "
          + "       COUNT(DISTINCT v.id) AS qtd_pedidos, "
          + "       SUM(vp.preco * vp.quantidade) AS faturamento_diario "
          + "FROM venda v "
          + "JOIN venda_produto vp ON v.id = vp.venda_id "
          + "WHERE v.data_hora BETWEEN ? AND ? "
          + "GROUP BY DATE(v.data_hora) "
          + "ORDER BY venda_data ASC";
}
