package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() throws ClassNotFoundException {
        Connection conn=null;
        Class.forName("com.mysql.jdbc.Driver");
        try {
            conn= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/file_transfer", "root","root");
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
