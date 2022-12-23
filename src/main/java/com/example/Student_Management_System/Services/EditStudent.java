package com.example.Student_Management_System.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("/EditServlet")
public class EditStudent extends HttpServlet  {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    int row;

    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse rsp ) throws IOException,ServletException
    {
        rsp.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        rsp.setHeader("Access-Control-Allow-Methods","PUT");
        rsp.setHeader("Access-Control-Allow-Headers","Content-Type");

        rsp.setContentType("application/json");
        PrintWriter out = rsp.getWriter();

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        StudentInfo student1 = new ObjectMapper().readValue(jb.toString(),StudentInfo.class);
        String id = student1.getStudent_id();
        String name = student1.getStudent_name();
        String fname = student1.getStudent_fname();
        String address= student1.getStudent_address();
        String sex = student1.getStudent_sex();
        String course = student1.getStudent_course();
        String pin = student1.getStudent_pin();
        String email = student1.getStudent_email();
        String mobile = student1.getStudent_mobile();
        String batch = student1.getStudent_batch();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/student","root","asdf123!ASDF");
            pst = con.prepareStatement("update student_info set student_name = ?,student_fname=?, student_address = ?,student_sex=?,student_course=?,student_pin=?,student_email=?,student_mobile=?,student_batch=? where student_id = ?");
            pst.setString(1, name);
            pst.setString(2, fname);
            pst.setString(3, address);
            pst.setString(4, sex);
            pst.setString(5,course);
            pst.setString(6,pin);
            pst.setString(7,email);
            pst.setString(8,mobile);
            pst.setString(9,batch);
            pst.setString(10, id);
            row = pst.executeUpdate();
            out.println("Data Updated Successfully !");

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);

        } catch (SQLException ex) {
            System.out.println(ex);
            out.println("<font color='red'>  Record Failed   </font>");
        }

    }

}
