package ecommerce.Database.Queries;

public class ProductQueries {
  public static String insertProductQuery =
      "INSERT INTO produto (descricao, preco, foto, quantidade, categoria_id) VALUES (?, ?, ?, ?, ?)";
  public static String selectProductByIdQuery = "SELECT * FROM produto WHERE id = ?";

  public static String updateProductQuery = 
  "UPDATE produto SET descricao = ?, preco = ?, foto = ?, quantidade = ?, categoria_id = ?, WHERE id =?";
}

