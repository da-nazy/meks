package com.example.meks_enginering.admin;

public class admin_dashboard_model {
    private int image;
    private String job_type;
    private String request_count;

    public admin_dashboard_model(int image, String job_type, String request_count) {
        this.image = image;
        this.job_type = job_type;
        this.request_count = request_count;
    }

    public int getImage() {
        return this.image;
    }

    public String getJob_type() {
        return this.job_type;
    }

    public String getRequest_count() {
        return this.request_count;
    }
}
