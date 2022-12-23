package com.example.Student_Management_System.MultiThread.MultiThreaded_Services;
import org.json.simple.JSONObject;
import java.sql.*;
import static java.lang.System.out;



public class parentService implements Runnable {
    public String parentThread;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    String id;

    public parentService(String eid) {
        this.id=eid;
    }
    public parentService() {

    }

    @Override
    public void run()     {
        int newId=Integer.parseInt(id);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/student","root","asdf123!ASDF");
            pst = con.prepareStatement("select * from parent_info where student_id = ?");
            pst.setInt(1, newId);
            rs = pst.executeQuery();

            while(rs.next()) {
                JSONObject record = new JSONObject();
                record.put("father_name", rs.getString("father_name"));
                record.put("mother_name", rs.getString("mothert_name"));
                record.put("father_phone", rs.getString("father_phone"));
                parentThread= String.valueOf(record);
            }

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            out.println("<font color='red'>  Record Failed   </font>");
        }
;
    }
}
