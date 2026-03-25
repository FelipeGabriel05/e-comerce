package ecommerce.Database;

import java.sql.*;

public class DBConnection {
  private static Connection connection = null;

  private DBConnection() {}

  public static synchronized Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
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

        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Database Connection established");
      }
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Something went wrong while connecting to the database");
      e.printStackTrace();
    }
    return connection;
  }
}
