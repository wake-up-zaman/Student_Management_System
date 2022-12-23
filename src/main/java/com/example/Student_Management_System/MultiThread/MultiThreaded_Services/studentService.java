package com.example.Student_Management_System.MultiThread.MultiThreaded_Services;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;

public class studentService implements Runnable {
    public String studentThread;
    Connection con;
    PreparedStatement pst;
    public ResultSet rs;
    String id;
    String parent1;
    String advisor1;

    public studentService(String eid, String parent, String advisor) {
        this.id = eid;
        this.parent1 = parent;
        this.advisor1 = advisor;
    }

    @Override
    public void run() {
        int newId = Integer.parseInt(id);
        JSONParser parser = new JSONParser();
        JSONObject parentJson;
        JSONObject advisorJson;
        JSONObject studentJson;

        try {
            parentJson = (JSONObject) parser.parse(parent1);
            advisorJson = (JSONObject) parser.parse(advisor1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/student", "root", "asdf123!ASDF");
            pst = con.prepareStatement("select * from student_info where student_id = ?");
            pst.setInt(1, newId);
            rs = pst.executeQuery();
            while (rs.next()) {
                JSONObject record = new JSONObject();
                record.put("student_id", rs.getInt("student_id"));
                record.put("student_name", rs.getString("student_name"));
                record.put("student_address", rs.getString("student_address"));
                record.put("student_program", rs.getString("student_course"));
                record.put("student_email", rs.getString("student_email"));
                record.put("student_mobile", rs.getString("student_mobile"));
                record.put("parentDetails",parentJson);
                record.put("advisorDetails",advisorJson);
                studentThread = String.valueOf(record);
            }
            try {
                studentJson = (JSONObject) parser.parse(studentThread);
                System.out.println("eeee" + studentJson);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }  catch (SQLException | ClassNotFoundException ex) {
            System.out.println("<font color='red'>  Record Failed   </font>");
        }
    }

};


//                student.setStudent_id("student_id", rs.getInt("student_id"));
//                        student.setStudent_name("student_name", rs.getString("student_name"));
//                        student.setStudent_address("student_address", rs.getString("student_address"));
//                        student.setStudent_course("student_program", rs.getString("student_course"));
//                        student.setStudent_email("student_email", rs.getString("student_email"));
//                        student.setStudent_mobile("student_mobile", rs.getString("student_mobile"));

