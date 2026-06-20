package ecommerce.Database.Queries;

public class CategoriesQueries {
  public static String insertCategoryQuery = "INSERT INTO categoria (descricao) values (?)";
  public static String updateCategoryQuery = "UPDATE categoria SET descricao = ? WHERE id = ?";
  public static String deleteCategoryQuery = "DELETE FROM categoria WHERE id = ?";
  public static String listCategoriesQuery = "SELECT * FROM categoria";
}
