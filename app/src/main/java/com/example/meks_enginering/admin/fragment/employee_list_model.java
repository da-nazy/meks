package com.example.meks_enginering.admin.fragment;

public class employee_list_model {
    String email;
    String fullName;
    String jobName;
    String userid;

    public employee_list_model(String fullName, String jobName) {
        this.fullName = fullName;
        this.jobName = jobName;
    }

    public employee_list_model(String fullName, String jobName, String userid, String email) {
        this.fullName = fullName;
        this.jobName = jobName;
        this.userid = userid;
        this.email = email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getJobName() {
        return this.jobName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUserid() {
        return this.userid;
    }
}
