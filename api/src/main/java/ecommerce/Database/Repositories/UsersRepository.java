package ecommerce.Database.Repositories;

import ecommerce.Database.Entites.User;
import ecommerce.Database.Queries.UsersQueries;
import ecommerce.Exceptions.DuplicateUserException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersRepository {

  private static final String SQLSTATE_UNIQUE_CONSTRAINT_VIOLATION = "23505";

  private Connection con;

  public UsersRepository(Connection dbConnection) {
    con = dbConnection;
  }

  public User createUser(User userInput) throws DuplicateUserException, SQLException {
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
      if (SQLSTATE_UNIQUE_CONSTRAINT_VIOLATION.equals(e.getSQLState())) {

        String errorMessage = e.getMessage().toLowerCase();

        if (errorMessage.contains("login")) {
          throw new DuplicateUserException("Login already exists");
        }

        if (errorMessage.contains("email")) {
          throw new DuplicateUserException("Email already exists");
        }

        throw new DuplicateUserException("User already exists");
      }
      throw e;
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

  public boolean deleteUserById(int id) {
    try {
      String query = UsersQueries.deleteUserQuery;
      PreparedStatement ps = con.prepareStatement(query);

      ps.setInt(1, id);

      int rowsAffected = ps.executeUpdate();

      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean updateUser(User user) {
    try {
      boolean changePassword = user.getSenha() != null;
      String query =
          changePassword
              ? UsersQueries.updateUserQuery
              : UsersQueries.updateUserWithoutPasswordQuery;
      PreparedStatement ps = con.prepareStatement(query);

      ps.setString(1, user.getNome());
      ps.setString(2, user.getEndereco());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getLogin());
      if (changePassword) {
        ps.setString(5, user.getSenha());
        ps.setInt(6, user.getId());
      } else {
        ps.setInt(5, user.getId());
      }

      int rowsAffected = ps.executeUpdate();

      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
