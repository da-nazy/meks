package com.example.meks_enginering.api;

import java.util.ArrayList;

public class getEmployee {
    ArrayList<employeeData> data = new ArrayList();
    String response_code;
    String response_desc;

    public String getResponse_code() {
        return this.response_code;
    }

    public String getResponse_desc() {
        return this.response_desc;
    }

    public ArrayList<employeeData> getData() {
        return this.data;
    }
}
