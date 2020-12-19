package com.example.civicproblemsapp;

public class UserDetails {

    public String name;
    public String emailId;
    public String mobile;
    public String password;


    public UserDetails() {

    }

    public UserDetails(String name, String emailId, String mobile, String password) {
        this.name = name;
        this.emailId = emailId;
        this.mobile = mobile;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
