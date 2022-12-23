package com.example.Student_Management_System.Authentication;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login", value = "/Login")
        public class Login extends HttpServlet {
            Connection con;
            PreparedStatement pst;
            ResultSet rs;
            int row;

            @Override
            protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
                res.setHeader("Access-Control-Allow-Origin", "*");
                res.setHeader("Access-Control-Allow-Methods", "GET, POST");
                res.setHeader("Access-Control-Allow-Headers", "Content-Type");
                res.setHeader("Access-Control-Max-Age", "86400");
                super.doOptions(req, res);
            }

            @Override
            public void doPost(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {

                //initialization
                rsp.setHeader("Access-Control-Allow-Origin", "*");
                rsp.setHeader("Access-Control-Allow-Methods", "*");
                rsp.setHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept");
                rsp.setContentType("application/json");
                req.setCharacterEncoding("UTF-8");
                PrintWriter out = rsp.getWriter();


                //Get the request value by using StringBuffer
                StringBuffer sent_value = new StringBuffer();
                String line = null;
                try {
                    BufferedReader reader = req.getReader();
                    while ((line = reader.readLine()) != null)
                        sent_value.append(line);
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println("jb: " + sent_value);

                //Convert the JSON File to String
                LoginInfo users = new ObjectMapper().readValue(sent_value.toString(), LoginInfo.class);
                String email = users.getUser_email();
                String password = users.getUser_password();


                //Connection with Database
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/student";
                    con = DriverManager.getConnection(url, "root", "asdf123!ASDF");

                    try{
                        pst = con.prepareStatement("select * from users where user_email=?");
                        pst.setString(1,email);
                        ResultSet rs= pst.executeQuery();


                        //Store the value in JSON Object
                        JSONObject jsonObject_UserName = new JSONObject();
                        while(rs.next()) {
                            jsonObject_UserName.put("user_name", rs.getString("user_name"));
                        }
                        System.out.println(jsonObject_UserName);

                        LoginName UserName = new ObjectMapper().readValue(jsonObject_UserName.toString(), LoginName.class);
                        String User_Name =  UserName.getUser_name();
                        System.out.println("User_Name:"+User_Name);

                        //Login Validation Result
                        if (UserValidation.validate(email, password)) {
                            try {

                                Instant now = Instant.now();
                                byte[] secret = Base64.getDecoder().decode("KMVMlwwK2t0Y0QkgwL6Pz9uWFZA/ET5JuB6FKS84g9vEQ9cv5EbaZICrtu9HCVvZtxhD4TX1pp71JlgbLGa+cg==");
                                System.out.println(secret);

                                String jwt = Jwts.builder()
                                        .setIssuer("Student Management System")
                                        .setAudience("All")
                                        .claim("jti", UUID.randomUUID().toString())
                                        .claim("Name",User_Name)
                                        .setIssuedAt(java.util.Date.from(now))
                                        .setExpiration(Date.from(now.plus(51, ChronoUnit.MINUTES)))
                                        .signWith(Keys.hmacShaKeyFor(secret))
                                        .compact();
//
                                System.out.println(jwt);

                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("Token", jwt);
                                jsonObject1.put("Login_Info", "Success");
                                jsonObject1.put("User_Name", User_Name);

                                out.println(jsonObject1);
                                System.out.println(jsonObject1);

                            }
                            catch (Exception e) {
                                System.out.println("login_Problem:" + e);
                            }
                        }
                        else {
                            JSONObject jsonObject2 = new JSONObject();
                            jsonObject2.put("Login_Info", "Failed");
                            out.println(jsonObject2);
                            System.out.println(jsonObject2);
//                  rsp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        }
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                } catch (SQLException ex) {
                    out.println("SQLException: Record Failed");
                }
            }

        }


