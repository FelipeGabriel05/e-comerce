package ecommerce.Database.Queries;

public class SessionsQueries {

  public static String insertSessionQuery =
      "INSERT INTO sessions (token, user_id, expires_at) VALUES (?, ?, ?)";

  public static String selectUserByTokenQuery =
      "SELECT u.* FROM usuario u"
          + " JOIN sessions s ON u.id = s.user_id"
          + " WHERE s.token = ? AND s.expires_at > ?";
}
