package ecommerce.Database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("Initializing Database...");
    String[] sqlFiles = getSqlFilePaths();

    try (Connection conn = DBConnection.getConnection()) {
      if (conn == null) {
        System.err.println("Could not establish connection to initialize database.");
        return;
      }
      try (Statement stmt = conn.createStatement()) {
        for (String sqlFile : sqlFiles) {
          System.out.println("Executing SQL file: " + sqlFile);
          String sql = loadSqlFile(sqlFile);
          if (sql != null && !sql.trim().isEmpty()) {
            stmt.execute(sql);
          }
        }
        System.out.println("Database initialization completed successfully.");
      }
    } catch (Exception e) {
      System.err.println("Error during database initialization:");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {}

  private String[] getSqlFilePaths() {
    List<String> sqlFiles = new ArrayList<>();
    String basePath = "ecommerce/Database/Tables/";
    String[] tableFiles = {"category.sql", "users.sql", "sessions.sql", "product.sql", "sales.sql"};
    String[] relationshipFiles = {"relationships/product-sales.sql"};
    String[] indexFiles = {"indexes/indexes.sql"};

    for (String file : tableFiles) {
      sqlFiles.add(basePath + file);
    }
    for (String file : relationshipFiles) {
      sqlFiles.add(basePath + file);
    }
    for (String file : indexFiles) {
      sqlFiles.add(basePath + file);
    }

    return sqlFiles.toArray(new String[0]);
  }

  private String loadSqlFile(String path) {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
      if (is == null) {
        System.err.println("SQL file not found: " + path);
        return null;
      }
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
        return reader.lines().collect(Collectors.joining("\n"));
      }
    } catch (Exception e) {
      System.err.println("Error reading SQL file: " + path);
      e.printStackTrace();
      return null;
    }
  }
}
