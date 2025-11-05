import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Correct connection string for XAMPP MySQL
    private static final String URL  = "jdbc:mysql://127.0.0.1:3306/ems_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";   // default XAMPP user
    private static final String PASS = "";       // default is empty (no password)

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // load MySQL driver (important for older Java)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // establish connection
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found. Make sure mysql-connector-j.jar is in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to database.");
            e.printStackTrace();
        }
        return conn;
    }
}
