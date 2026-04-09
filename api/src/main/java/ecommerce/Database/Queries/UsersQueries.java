package ecommerce.Database.Queries;

public class UsersQueries {
  public static String selectUserQuery = "SELECT * FROM usuario WHERE login = ? AND senha = ?";

  public static String selectUserByIdQuery = "SELECT * FROM usuario WHERE id = ?";

  public static String insertUserQuery =
      "INSERT INTO usuario (nome, endereco, email, login, senha, administrador) VALUES (?, ?, ?, ?, ?, ?)";
}
