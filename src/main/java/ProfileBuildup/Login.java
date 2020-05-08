package ProfileBuildup;

import Database.DatabaseConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends HttpServlet {
    String email;
    String password;
    boolean loginValidation;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      email = request.getParameter("emailId");
      password = request.getParameter("passwordId");
        try {
            loginValidation = authenticate(email,password);
            if(loginValidation == true){
                System.out.println("came");
                HttpSession session=request.getSession();
                session.setAttribute("email",email);
                response.sendRedirect("Profile.jsp");
            } else{

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public boolean authenticate(String email,String password) throws SQLException {
        Connection conn = null;
        DatabaseConnection db = new DatabaseConnection();
        try{
            conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * from users where email=? and password=?");
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        return false;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
