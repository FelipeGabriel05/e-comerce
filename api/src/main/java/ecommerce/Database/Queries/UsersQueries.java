package ecommerce.Database.Queries;

public class UsersQueries {
  public static String selectUserQuery = "SELECT * FROM users WHERE login = ? AND senha = ?";

  public static String insertUserQuery =
      "INSERT INTO users (nome, endereco, email, login, senha, administrador) VALUES (?, ?, ?, ?, ?, ?)";
}
