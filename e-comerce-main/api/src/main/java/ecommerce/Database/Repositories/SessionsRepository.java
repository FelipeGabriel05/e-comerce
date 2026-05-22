package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.User;
import ecommerce.Database.Queries.SessionsQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionsRepository {

  private final Connection con;

  public SessionsRepository(Connection con) {
    this.con = con;
  }

  public void createSession(String token, int userId, long expiresAt) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SessionsQueries.insertSessionQuery);
    ps.setString(1, token);
    ps.setInt(2, userId);
    ps.setLong(3, expiresAt);
    ps.executeUpdate();
  }

  public User findUserByToken(String token) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SessionsQueries.selectUserByTokenQuery);
    ps.setString(1, token);
    ps.setLong(2, System.currentTimeMillis() / 1000);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      User user = new User();
      user.setId(rs.getInt("id"));
      user.setNome(rs.getString("nome"));
      user.setEndereco(rs.getString("endereco"));
      user.setEmail(rs.getString("email"));
      user.setLogin(rs.getString("login"));
      user.setSenha(rs.getString("senha"));
      user.setAdministrador(rs.getBoolean("administrador"));
      return user;
    }
    return null;
  }
}
