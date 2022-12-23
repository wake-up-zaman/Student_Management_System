package com.example.Student_Management_System.Services;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("/Editreturn")
public class EditInfo extends HttpServlet {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    int row;

    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse rsp ) throws IOException,ServletException
    {
        rsp.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        rsp.setContentType("text/html");
        PrintWriter out = rsp.getWriter();

        String eid = req.getParameter("id");
        int newId=Integer.parseInt(eid);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/student","root","asdf123!ASDF");
            pst = con.prepareStatement("select * from student_info where student_id = ?");
            pst.setInt(1, newId);
            rs = pst.executeQuery();

            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();

            while(rs.next()) {
                JSONObject record = new JSONObject();
                record.put("student_id", rs.getInt("student_id"));
                System.out.println(rs.getInt("student_id"));
                record.put("student_name", rs.getString("student_name"));
                record.put("student_fname", rs.getString("student_fname"));
                record.put("student_address", rs.getString("student_address"));
                record.put("student_sex", rs.getString("student_sex"));
                record.put("student_course", rs.getString("student_course"));
                record.put("student_pin", rs.getString("student_pin"));
                record.put("student_email", rs.getString("student_email"));
                record.put("student_mobile", rs.getString("student_mobile"));
                record.put("student_batch", rs.getString("student_batch"));
                array.add(record);
            }
            jsonObject.put("Student_data", array);
            out.println( jsonObject.put("Student_data", array));
            out.flush();

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            out.println("<font color='red'>  Record Failed   </font>");
        }
    }

}




