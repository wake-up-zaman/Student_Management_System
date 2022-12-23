package com.example.Student_Management_System.Authentication;

public class LoginName {

    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    @Override
    public String toString() {
        return "LoginInfo{" +
                "user_name='" + user_name + '\'' +
                '}';
    }
}
