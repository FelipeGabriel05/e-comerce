package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.User;
import ecommerce.Database.Repositories.SessionsRepository;
import ecommerce.Database.Repositories.UsersRepository;
import java.security.SecureRandom;
import java.sql.Connection;

public class LoginUseCase {
  public static final int ONE_DAY = 60 * 60 * 24;

  private static final int SESSION_TTL_SECONDS = ONE_DAY;

  public record LoginResult(String token, User user) {}

  public LoginResult execute(String login, String senha) {
    try {
      Connection dbConnection = DBConnection.getConnection();
      UsersRepository usersRepository = new UsersRepository(dbConnection);
      User user = usersRepository.findUserByLoginAndSenha(login, senha);

      if (user == null) return null;

      String token = generateToken();
      long expiresAt = (System.currentTimeMillis() / 1000) + SESSION_TTL_SECONDS;
      new SessionsRepository(dbConnection).createSession(token, user.getId(), expiresAt);
			user.setSenha(null);
      return new LoginResult(token, user);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public User findUserBySessionToken(String token) {
    try {
      Connection dbConnection = DBConnection.getConnection();
      return new SessionsRepository(dbConnection).findUserByToken(token);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private String generateToken() {
    byte[] randomBytes = new byte[32];
    new SecureRandom().nextBytes(randomBytes);
    return bytesToHex(randomBytes);
  }

  private String bytesToHex(byte[] bytes) {
    StringBuilder hex = new StringBuilder();
    for (byte b : bytes) {
      hex.append(String.format("%02x", b));
    }
    return hex.toString();
  }
}
