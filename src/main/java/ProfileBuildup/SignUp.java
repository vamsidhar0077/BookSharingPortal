package ProfileBuildup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Database.DatabaseConnection;
public class SignUp extends HttpServlet {
    String name;
    String email;
    String password;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        name = request.getParameter("user");
        email = request.getParameter("email");
        password = request.getParameter("password");
        Connection conn = null;
        DatabaseConnection db = new DatabaseConnection();
        try{
            conn = db.getConnection();
            System.out.println("connection created");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username,email,password) VALUES (?,?,?)");
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,password);
            ps.execute();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("index.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
