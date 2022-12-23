package com.example.Student_Management_System.Services;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class StudentInfo {
    private String student_id;
    private String student_name;
    private String student_fname;
    private String student_address;
    private String student_sex;
    private String student_course;
    private String student_batch;
    private String student_pin;
    private String student_email;
    private String student_mobile;

    public String getStudent_id() {
        return this.student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return this.student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_fname() {
        return student_fname;
    }

    public void setStudent_fname(String student_fname) {
        this.student_fname = student_fname;
    }

    public String getStudent_address() {
        return student_address;
    }

    public void setStudent_address(String student_address) {
        this.student_address = student_address;
    }

    public String getStudent_sex() {
        return student_sex;
    }

    public void setStudent_sex(String student_sex) {
        this.student_sex = student_sex;
    }

    public String getStudent_course() {
        return student_course;
    }

    public void setStudent_course(String student_course) {
        this.student_course = student_course;
    }

    public String getStudent_batch() {
        return student_batch;
    }

    public void setStudent_batch(String student_batch) {
        this.student_batch = student_batch;
    }

    public String getStudent_pin() {
        return student_pin;
    }

    public void setStudent_pin(String student_pin) {
        this.student_pin = student_pin;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_mobile() {
        return student_mobile;
    }

    public void setStudent_mobile(String student_mobile) {
        this.student_mobile = student_mobile;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "student_id='" + student_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", student_fname='" + student_fname + '\'' +
                ", student_address='" + student_address + '\'' +
                ", student_sex='" + student_sex + '\'' +
                ", student_course='" + student_course + '\'' +
                ", student_batch='" + student_batch + '\'' +
                ", student_pin='" + student_pin + '\'' +
                ", student_email='" + student_email + '\'' +
                ", student_mobile='" + student_mobile + '\'' +
                '}';
    }



    @WebServlet(name = "com.example.servletproject2.StudentInfo.JWTServlet", value = "/com.example.servletproject2.StudentInfo.JWTServlet")
    public static class JWTServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        }
    }
}
