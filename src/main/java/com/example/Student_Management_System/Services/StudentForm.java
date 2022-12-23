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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



@WebServlet(name = "StudentForm", value = "/StudentForm")
public class StudentForm extends HttpServlet {
    Connection con;
    PreparedStatement pst;
    int row;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
        rsp.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        rsp.setHeader("Access-Control-Allow-Methods","POST,OPTIONS");
        rsp.setHeader("Access-Control-Allow-Headers","Content-Type");
        rsp.setContentType("application/json");
        System.out.println("Request :"+req);
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

        System.out.println("jb11"+jb);

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
        System.out.println(batch);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/student";
            con = DriverManager.getConnection(url, "root", "asdf123!ASDF");

            pst = con.prepareStatement("insert into student_info(student_id,student_name,student_fname,student_address,student_sex,student_course,student_pin,student_email,student_mobile,student_batch)values(?,?,?,?,?,?,?,?,?,?) ");
            pst.setString(1, id);
            pst.setString(2, name);
            pst.setString(3, fname);
            pst.setString(4, address);
            pst.setString(5, sex);
            pst.setString(6,course);
            pst.setString(7,pin);
            pst.setString(8,email);
            pst.setString(9,mobile);
            pst.setString(10,batch);
            row = pst.executeUpdate();
            out.println("Data added Successfully !");

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        catch (SQLException ex) {
            System.out.println(ex);
            out.println("<font color='red'>  adding student Failed   </font>");
        }
    }
}
