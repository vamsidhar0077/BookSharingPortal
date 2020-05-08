package Services;

import Database.DatabaseConnection;
import javax.servlet.ServletException;
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
import java.util.ArrayList;
import java.util.List;

public class DisplayFiles extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email;
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(false);
        email=(String)session.getAttribute("email");
        System.out.println(email);
        List<String> filesList = new ArrayList<String>();
        Connection conn;
        DatabaseConnection db = new DatabaseConnection();
        try{
            conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT file_name from storage where email =?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                filesList.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
            System.out.println(filesList.size());
            String json = new Gson().toJson(filesList);
            System.out.println(json);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            conn.close();
            out.write(json);
            out.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
