package com.example.Student_Management_System.MultiThread.MultiThreaded_Services;
import org.json.simple.JSONObject;
import java.sql.*;
import static java.lang.System.out;

public class advisorService implements Runnable {
    public String advisorThread;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    String id;

    public advisorService(String eid) {
        this.id=eid;
    }
    public advisorService() {

    }

    @Override
    public void run(){
        int newId=Integer.parseInt(id);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/student","root","asdf123!ASDF");
            pst = con.prepareStatement("select * from advisor_info where student_id = ?");
            pst.setInt(1, newId);
            rs = pst.executeQuery();

            while(rs.next()) {
                JSONObject record = new JSONObject();
                record.put("advisor_name", rs.getString("advisor_name"));
                record.put("advisor_email", rs.getString("advisor_email"));
                record.put("research_field", rs.getString("research_field"));
                advisorThread= String.valueOf(record);
            }
        }
        catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            out.println("<font color='red'>  Record Failed   </font>");
        }

    }
}
