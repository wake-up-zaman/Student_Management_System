package com.example.Student_Management_System.Authentication;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "LoginPage", value = "/LoginPage")
public class UserValidation extends HttpServlet {

    public static boolean validate(String email, String password){
        boolean status=false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/student";
            Connection con= DriverManager.getConnection(url, "root", "asdf123!ASDF");
            PreparedStatement pst= con.prepareStatement("select * from users where user_email=? and user_password=?");
            pst.setString(1,email);
            pst.setString(2,password);
            ResultSet rs= pst.executeQuery();
            status=rs.next();
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println(status);
        return status;
    }
}
