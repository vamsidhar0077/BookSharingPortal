package Services;

import Database.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("hello");
        String name = null;
        String email;
        response.setContentType("text/html");
        HttpSession session=request.getSession(false);
        email=(String)session.getAttribute("email");
        Connection conn;
        DatabaseConnection db = new DatabaseConnection();
        try{
            conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT username from users where email=?");
            ps.setString(1,email);
            ResultSet rs;
            rs = ps.executeQuery();
            if(rs.next()) name = rs.getString(1);
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(name);
        response.setContentType("text");
        response.setContentLength(name.length());
        PrintWriter out = response.getWriter();
        out.write(name);
        out.close();

    }
}
