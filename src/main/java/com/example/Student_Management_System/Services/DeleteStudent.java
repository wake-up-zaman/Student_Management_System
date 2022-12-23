package com.example.Student_Management_System.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintWriter;

import java.security.Key;
import java.sql.*;
import java.util.Base64;

@WebServlet(name = "Delete", value = "/Delete")
public class DeleteStudent extends HttpServlet {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    int row;


    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST,DELETE");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        res.setHeader("Access-Control-Max-Age", "86400");
        super.doOptions(req, res);
    }

    public void doPost(HttpServletRequest req,HttpServletResponse rsp ) throws IOException,ServletException
    {
        rsp.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        rsp.setHeader("Access-Control-Allow-Methods","GET, POST,DELETE");
        rsp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

        rsp.setContentType("application/json");
        PrintWriter out = rsp.getWriter();

        String token=req.getHeader("Authorization");
        System.out.println(token);
        String newToken=token.replaceAll("Bearer ","");
        System.out.println("Token:"+newToken);

        try{
            String secret = "KMVMlwwK2t0Y0QkgwL6Pz9uWFZA/ET5JuB6FKS84g9vEQ9cv5EbaZICrtu9HCVvZtxhD4TX1pp71JlgbLGa+cg==";
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                    SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(newToken);

            System.out.println(jwt);

            String eid = req.getParameter("id");
            int newId=Integer.parseInt(eid);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/student","root","asdf123!ASDF");
                pst = con.prepareStatement("delete from student_info where student_id = ?");
                pst.setInt(1, newId);
                row = pst.executeUpdate();

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("Delete_Info", "Token is Valid");
                out.println(jsonObject1);
                System.out.println(jsonObject1);

            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            } catch (SQLException ex) {

                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("Delete_Info", "SQL Exception");
                out.println(jsonObject2);
                System.out.println(jsonObject2);

            }
        }
        catch(Exception e){
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("Delete_Info", "Token is Invalid");
            out.println(jsonObject3);
            System.out.println(e);
    }


    }

}
