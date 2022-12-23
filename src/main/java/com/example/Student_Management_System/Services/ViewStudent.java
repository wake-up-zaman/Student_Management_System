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


@WebServlet(name = "viewStudent", value = "/viewStudent")
public class ViewStudent extends HttpServlet {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    int row;
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse rsp ) throws IOException,ServletException
    {
        rsp.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        rsp.setContentType("application/json");
        PrintWriter out = rsp.getWriter();

        try {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/student";
        con = DriverManager.getConnection(url, "root", "asdf123!ASDF");
        String sql;
        try{
        sql = "select * from student_info";
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        while(rs.next()) {
        JSONObject record = new JSONObject();
        record.put("student_id", rs.getInt("student_id"));
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
        }
        catch(Exception e) {
        out.println(e);
        }
        } catch (ClassNotFoundException ex) {
        out.println(ex);
        } catch (SQLException ex) {
        out.println("<font color='red'>  Record Failed   </font>");
        }
        }
    }

