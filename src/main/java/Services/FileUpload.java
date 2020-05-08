package Services;
import Database.DatabaseConnection;
import com.oreilly.servlet.MultipartRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@MultipartConfig
public class FileUpload extends HttpServlet {
    String email;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = null;
        String fileName= null;
        HttpSession session = request.getSession(false);
        email = (String) session.getAttribute("email");
        System.out.println(email);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String path="C:\\Users\\Reddy gadu\\Desktop\\DynamicOutsourcedProject\\Storage";
        MultipartRequest m = new MultipartRequest(request,path);
        Enumeration files = m.getFileNames();
        Connection conn = null;
        DatabaseConnection db = new DatabaseConnection();
        while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            fileName = m.getFilesystemName(name);
            File myFile = m.getFile(name);
            FileReader fr=new FileReader(myFile);
            System.out.println(myFile.length());
            InputStream inputStream = new FileInputStream(myFile);
            try {
                System.out.println("hello");
                conn = db.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT  into storage (email,file_name,file_data,file_path) values(?,?,?,?)");
                ps.setString(1,email);
                ps.setString(2,fileName);
                System.out.println("hello1");
                ps.setCharacterStream(3,fr,(int)myFile.length());
                ps.setString(4,path+"\\"+fileName);
                int resultSet = ps.executeUpdate();
                if(resultSet != 0)
                {
                    out.write("Succesfully uploaded"+fileName);
                    out.close();
                }
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
