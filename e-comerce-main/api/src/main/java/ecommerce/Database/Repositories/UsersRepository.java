package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.User;
import ecommerce.Database.Queries.UsersQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersRepository {
  private Connection con;

  public UsersRepository(Connection dbConnection) {
    con = dbConnection;
  }

  public User createUser(User userInput) {
    try {
      String query = UsersQueries.insertUserQuery;
      PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, userInput.getNome());
      ps.setString(2, userInput.getEndereco());
      ps.setString(3, userInput.getEmail());
      ps.setString(4, userInput.getLogin());
      ps.setString(5, userInput.getSenha());
      ps.setBoolean(6, userInput.isAdministrador());

      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();

      User user = new User();

      if (rs.next()) {
        user.setId(rs.getInt(1));
      }

      user.setNome(userInput.getNome());
      user.setEndereco(userInput.getEndereco());
      user.setEmail(userInput.getEmail());
      user.setLogin(userInput.getLogin());
      user.setSenha(userInput.getSenha());
      user.setAdministrador(userInput.isAdministrador());

      return user;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public User findUserByLoginAndSenha(String login, String senha) {
    try {
      String query = UsersQueries.selectUserQuery;
      PreparedStatement ps = con.prepareStatement(query);

      ps.setString(1, login);
      ps.setString(2, senha);

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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public User findUserById(int id) {
    try {
      String query = UsersQueries.selectUserByIdQuery;
      PreparedStatement ps = con.prepareStatement(query);

      ps.setInt(1, id);

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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
