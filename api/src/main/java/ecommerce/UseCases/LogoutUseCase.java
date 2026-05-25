package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Repositories.SessionsRepository;
import java.sql.Connection;

public class LogoutUseCase {
  public void execute(String token) throws Exception {
    Connection dbConnection = DBConnection.getConnection();

    SessionsRepository sessionsRepository = new SessionsRepository(dbConnection);

    sessionsRepository.deleteSessionByToken(token);
  }
}
