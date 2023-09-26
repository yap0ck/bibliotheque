import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory{
    private static final String URL = "jdbc:postgresql://localhost:5432/bibliotheque";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }
}

