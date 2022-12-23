package com.example.Student_Management_System.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "Registration", value = "/Registration")
public class Registration extends HttpServlet {

    Connection con;
    PreparedStatement pst;
    int row;

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{

        doPost(req,res);
    }

    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse rsp ) throws IOException,ServletException
    {
        rsp.setHeader("Access-Control-Allow-Origin","*");
        rsp.setHeader("Access-Control-Allow-Methods","*");
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

        UserInfo users = new ObjectMapper().readValue(jb.toString(),UserInfo.class);
        String name = users.getUser_name();
        String email = users.getUser_email();
        String password = users.getUser_password();


        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/student";
            con = DriverManager.getConnection(url, "root", "asdf123!ASDF");
            pst = con.prepareStatement("insert into users(user_name,user_email,user_password)values(?,?,?) ");
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);
            row = pst.executeUpdate();

            try {

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("SignUp_Info", "Success");
                out.println(jsonObject1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
       catch (SQLException ex) {
           System.out.println(ex);
           JSONObject jsonObject2 = new JSONObject();
           jsonObject2.put("SignUp_Info", "Failed");
           out.println(jsonObject2);
      }
    }
}
