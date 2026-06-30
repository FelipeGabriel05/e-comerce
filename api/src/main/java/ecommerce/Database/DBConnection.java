package ecommerce.Database;

import java.sql.*;

public class DBConnection {
  private static Connection connection = null;

  private DBConnection() {}

  public static synchronized Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        connection = openConnection();
        System.out.println("Database Connection established");
      }
    } catch (SQLException e) {
      System.out.println("Something went wrong while connecting to the database");
      e.printStackTrace();
    }
    return connection;
  }

  public static Connection getNewConnection() throws SQLException {
    return openConnection();
  }

  public static void closeConnection(Connection con) {
    if (con == null || con == connection) {
      return;
    }
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static Connection openConnection() throws SQLException {
    String driver = "org.postgresql.Driver";
    String url =
        System.getenv("DB_URL") != null
            ? System.getenv("DB_URL")
            : "jdbc:postgresql://postgres:5432/ecommerceproject";
    String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
    String password =
        System.getenv("DB_PASSWORD") != null
            ? System.getenv("DB_PASSWORD")
            : "ecommerce_secret_password_123";

    try {
      Class.forName(driver);
      return DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException e) {
      throw new SQLException("PostgreSQL JDBC driver not found: " + driver, e);
    }
  }
}
