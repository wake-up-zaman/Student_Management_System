package com.example.Student_Management_System.MultiThread;
import com.example.Student_Management_System.MultiThread.MultiThreaded_Services.advisorService;
import com.example.Student_Management_System.MultiThread.MultiThreaded_Services.parentService;
import com.example.Student_Management_System.MultiThread.MultiThreaded_Services.studentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Student", value = "/Student")
public class Student extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse rsp ) throws IOException,ServletException {

        //request handling
        rsp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        rsp.setContentType("application/json");
        PrintWriter out = rsp.getWriter();
        String eid = req.getParameter("id");


        //services
        parentService parent_info = new parentService(eid);
        advisorService advisor_info = new advisorService(eid);

        //threads
        Thread t1 = new Thread(parent_info);
        Thread t2 = new Thread(advisor_info);
        t1.start();
        t2.start();
        try{t2.join();}
        catch(InterruptedException e){e.printStackTrace();}

        String parent=parent_info.parentThread;
        String advisor=advisor_info.advisorThread;
        studentService student_info= new studentService(eid,parent,advisor);

        Thread t3 = new Thread(student_info);
        t3.start();
        try{t3.join();}
        catch(InterruptedException e){e.printStackTrace();}

        String student=student_info.studentThread;


//        printing
        out.println(student);

    }
}
